/*
 * Copyright 2013 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaplanner.core.impl.score.buildin.bendable;

import java.util.Arrays;

import org.optaplanner.core.api.score.Score;
import org.optaplanner.core.api.score.buildin.bendable.BendableScore;
import org.optaplanner.core.api.score.buildin.bendable.BendableScoreHolder;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;
import org.optaplanner.core.api.score.holder.ScoreHolder;
import org.optaplanner.core.impl.score.definition.AbstractFeasibilityScoreDefinition;
import org.optaplanner.core.impl.score.definition.AbstractScoreDefinition;
import org.optaplanner.core.impl.score.trend.InitializingScoreTrend;
import org.optaplanner.core.impl.score.trend.InitializingScoreTrendLevel;

public class BendableScoreDefinition extends AbstractFeasibilityScoreDefinition<BendableScore> {

    private final int hardLevelCount;
    private final int softLevelCount;

    private double recursiveTimeGradientWeight = 0.50; // TODO this is a guess

    public BendableScoreDefinition(int hardLevelCount, int softLevelCount) {
        this.hardLevelCount = hardLevelCount;
        this.softLevelCount = softLevelCount;
    }

    public int getHardLevelCount() {
        return hardLevelCount;
    }

    public int getSoftLevelCount() {
        return softLevelCount;
    }

    public double getRecursiveTimeGradientWeight() {
        return recursiveTimeGradientWeight;
    }

    /**
     * It's recommended to use a number which can be exactly represented as a double,
     * such as 0.5, 0.25, 0.75, 0.125, ... but not 0.1, 0.2, ...
     * @param recursiveTimeGradientWeight 0.0 <= recursiveTimeGradientWeight <= 1.0
     */
    public void setRecursiveTimeGradientWeight(double recursiveTimeGradientWeight) {
        this.recursiveTimeGradientWeight = recursiveTimeGradientWeight;
        if (recursiveTimeGradientWeight < 0.0 || recursiveTimeGradientWeight > 1.0) {
            throw new IllegalArgumentException("Property recursiveTimeGradientWeight (" + recursiveTimeGradientWeight
                    + ") must be greater or equal to 0.0 and smaller or equal to 1.0.");
        }
    }

    // ************************************************************************
    // Worker methods
    // ************************************************************************

    @Override
    public int getLevelCount() {
        return hardLevelCount + softLevelCount;
    }

    @Override
    public int getFeasibleLevelCount() {
        return hardLevelCount;
    }

    public Class<BendableScore> getScoreClass() {
        return BendableScore.class;
    }

    public BendableScore parseScore(String scoreString) {
        return BendableScore.parseScore(hardLevelCount, softLevelCount, scoreString);
    }

    public BendableScore createScore(int... scores) {
        int levelCount = hardLevelCount + softLevelCount;
        if (scores.length != levelCount) {
            throw new IllegalArgumentException("The scores (" + Arrays.toString(scores)
                    + ")'s length (" + scores.length
                    + ") is not levelCount (" + levelCount + ").");
        }
        return BendableScore.valueOf(Arrays.copyOfRange(scores, 0, hardLevelCount),
                Arrays.copyOfRange(scores, hardLevelCount, levelCount));
    }

    public BendableScoreHolder buildScoreHolder(boolean constraintMatchEnabled) {
        return new BendableScoreHolder(constraintMatchEnabled, hardLevelCount, softLevelCount);
    }

    public BendableScore buildOptimisticBound(InitializingScoreTrend initializingScoreTrend, BendableScore score) {
        InitializingScoreTrendLevel[] trendLevels = initializingScoreTrend.getTrendLevels();
        int[] hardScores = new int[hardLevelCount];
        for (int i = 0; i < hardLevelCount; i++) {
            hardScores[i] = (trendLevels[i] == InitializingScoreTrendLevel.ONLY_DOWN)
                    ? score.getHardScore(i) : Integer.MAX_VALUE;
        }
        int[] softScores = new int[softLevelCount];
        for (int i = 0; i < softLevelCount; i++) {
            softScores[i] = (trendLevels[hardLevelCount + i] == InitializingScoreTrendLevel.ONLY_DOWN)
                    ? score.getSoftScore(i) : Integer.MAX_VALUE;
        }
        return BendableScore.valueOf(hardScores, softScores);
    }

    public BendableScore buildPessimisticBound(InitializingScoreTrend initializingScoreTrend, BendableScore score) {
        InitializingScoreTrendLevel[] trendLevels = initializingScoreTrend.getTrendLevels();
        int[] hardScores = new int[hardLevelCount];
        for (int i = 0; i < hardLevelCount; i++) {
            hardScores[i] = (trendLevels[i] == InitializingScoreTrendLevel.ONLY_UP)
                    ? score.getHardScore(i) : Integer.MIN_VALUE;
        }
        int[] softScores = new int[softLevelCount];
        for (int i = 0; i < softLevelCount; i++) {
            softScores[i] = (trendLevels[hardLevelCount + i] == InitializingScoreTrendLevel.ONLY_UP)
                    ? score.getSoftScore(i) : Integer.MIN_VALUE;
        }
        return BendableScore.valueOf(hardScores, softScores);
    }

}
