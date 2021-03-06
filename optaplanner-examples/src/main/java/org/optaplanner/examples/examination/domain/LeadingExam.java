/*
 * Copyright 2014 JBoss Inc
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

package org.optaplanner.examples.examination.domain;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;
import org.optaplanner.examples.examination.domain.solver.ExamDifficultyWeightFactory;
import org.optaplanner.examples.examination.domain.solver.PeriodUpdatingVariableListener;
import org.optaplanner.examples.vehiclerouting.domain.solver.VehicleUpdatingVariableListener;
import org.optaplanner.examples.vehiclerouting.domain.timewindowed.solver.ArrivalTimeUpdatingVariableListener;

@PlanningEntity
@XStreamAlias("LeadingExam")
public class LeadingExam extends Exam {

    protected List<FollowingExam> followingExamList;

    // Planning variables: changes during planning, between score calculations.
    protected Period period;

    public List<FollowingExam> getFollowingExamList() {
        return followingExamList;
    }

    public void setFollowingExamList(List<FollowingExam> followingExamList) {
        this.followingExamList = followingExamList;
    }

    @PlanningVariable(valueRangeProviderRefs = {"periodRange"},
            variableListenerClasses = {PeriodUpdatingVariableListener.class})
    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

}
