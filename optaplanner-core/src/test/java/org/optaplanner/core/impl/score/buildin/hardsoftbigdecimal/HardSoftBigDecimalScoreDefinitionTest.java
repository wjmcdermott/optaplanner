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

package org.optaplanner.core.impl.score.buildin.hardsoftbigdecimal;

import java.math.BigDecimal;

import org.junit.Test;
import org.optaplanner.core.api.score.buildin.hardsoftbigdecimal.HardSoftBigDecimalScore;

import static org.junit.Assert.assertEquals;

public class HardSoftBigDecimalScoreDefinitionTest {

    @Test
    public void getLevelCount() {
        assertEquals(2, new HardSoftBigDecimalScoreDefinition().getLevelCount());
    }

    @Test
    public void getFeasibleLevelCount() {
        assertEquals(1, new HardSoftBigDecimalScoreDefinition().getFeasibleLevelCount());
    }

}
