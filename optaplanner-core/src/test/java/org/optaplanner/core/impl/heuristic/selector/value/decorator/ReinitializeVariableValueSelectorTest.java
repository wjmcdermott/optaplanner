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

package org.optaplanner.core.impl.heuristic.selector.value.decorator;

import org.junit.Test;
import org.optaplanner.core.impl.domain.entity.PlanningEntityDescriptor;
import org.optaplanner.core.impl.domain.variable.PlanningVariableDescriptor;
import org.optaplanner.core.impl.heuristic.selector.SelectorTestUtils;
import org.optaplanner.core.impl.heuristic.selector.common.SelectionCacheType;
import org.optaplanner.core.impl.heuristic.selector.entity.decorator.NullValueReinitializeVariableEntityFilter;
import org.optaplanner.core.impl.heuristic.selector.value.EntityIndependentValueSelector;
import org.optaplanner.core.impl.heuristic.selector.value.ValueSelector;
import org.optaplanner.core.impl.phase.AbstractSolverPhaseScope;
import org.optaplanner.core.impl.phase.step.AbstractStepScope;
import org.optaplanner.core.impl.solver.scope.DefaultSolverScope;
import org.optaplanner.core.impl.testdata.domain.TestdataEntity;
import org.optaplanner.core.impl.testdata.domain.TestdataValue;
import org.optaplanner.core.impl.testdata.domain.multivar.TestdataMultiVarEntity;

import static org.mockito.Mockito.*;
import static org.optaplanner.core.impl.testdata.util.PlannerAssert.*;

public class ReinitializeVariableValueSelectorTest {

    @Test
    public void oneVariable() {
        PlanningEntityDescriptor entityDescriptor = TestdataEntity.buildEntityDescriptor();
        PlanningVariableDescriptor variableDescriptor = entityDescriptor.getVariableDescriptor("value");
        TestdataEntity e1 = new TestdataEntity("e1");
        TestdataEntity e2 = new TestdataEntity("e2");
        TestdataValue v1 = new TestdataValue("v1");
        TestdataValue v2 = new TestdataValue("v2");
        TestdataValue v3 = new TestdataValue("v3");
        ValueSelector childValueSelector = SelectorTestUtils.mockValueSelector(variableDescriptor,
                v1, v2, v3);

        NullValueReinitializeVariableEntityFilter reinitializeVariableEntityFilter
                = new NullValueReinitializeVariableEntityFilter(childValueSelector.getVariableDescriptor());
        ValueSelector valueSelector = new ReinitializeVariableValueSelector(childValueSelector,
                reinitializeVariableEntityFilter);

        DefaultSolverScope solverScope = mock(DefaultSolverScope.class);
        valueSelector.solvingStarted(solverScope);

        AbstractSolverPhaseScope phaseScopeA = mock(AbstractSolverPhaseScope.class);
        when(phaseScopeA.getSolverScope()).thenReturn(solverScope);
        valueSelector.phaseStarted(phaseScopeA);

        AbstractStepScope stepScopeA1 = mock(AbstractStepScope.class);
        when(stepScopeA1.getPhaseScope()).thenReturn(phaseScopeA);
        valueSelector.stepStarted(stepScopeA1);
        assertAllCodesOfValueSelectorForEntity(valueSelector, e1, "v1", "v2", "v3");
        valueSelector.stepEnded(stepScopeA1);

        AbstractStepScope stepScopeA2 = mock(AbstractStepScope.class);
        when(stepScopeA2.getPhaseScope()).thenReturn(phaseScopeA);
        valueSelector.stepStarted(stepScopeA2);
        e2.setValue(v2);
        assertAllCodesOfValueSelectorForEntity(valueSelector, e2);
        valueSelector.stepEnded(stepScopeA2);

        valueSelector.phaseEnded(phaseScopeA);

        AbstractSolverPhaseScope phaseScopeB = mock(AbstractSolverPhaseScope.class);
        when(phaseScopeB.getSolverScope()).thenReturn(solverScope);
        valueSelector.phaseStarted(phaseScopeB);

        AbstractStepScope stepScopeB1 = mock(AbstractStepScope.class);
        when(stepScopeB1.getPhaseScope()).thenReturn(phaseScopeB);
        valueSelector.stepStarted(stepScopeB1);
        e2.setValue(null);
        assertAllCodesOfValueSelectorForEntity(valueSelector, e2, "v1", "v2", "v3");
        valueSelector.stepEnded(stepScopeB1);


        AbstractStepScope stepScopeB2 = mock(AbstractStepScope.class);
        when(stepScopeB2.getPhaseScope()).thenReturn(phaseScopeB);
        valueSelector.stepStarted(stepScopeB2);
        e1.setValue(v3);
        assertAllCodesOfValueSelectorForEntity(valueSelector, e1);
        valueSelector.stepEnded(stepScopeB2);

        valueSelector.phaseEnded(phaseScopeB);

        valueSelector.solvingEnded(solverScope);

        verifySolverPhaseLifecycle(childValueSelector, 1, 2, 4);
        verify(childValueSelector, atMost(4)).iterator(any());
        verify(childValueSelector, atMost(4)).getSize(any());
    }

    @Test
    public void multiVariable() {
        PlanningEntityDescriptor entityDescriptor = TestdataMultiVarEntity.buildEntityDescriptor();
        PlanningVariableDescriptor variableDescriptor = entityDescriptor.getVariableDescriptor("secondaryValue");
        TestdataMultiVarEntity e1 = new TestdataMultiVarEntity("e1");
        TestdataMultiVarEntity e2 = new TestdataMultiVarEntity("e2");
        TestdataValue p1 = new TestdataValue("p1");
        TestdataValue s1 = new TestdataValue("s1");
        TestdataValue s2 = new TestdataValue("s2");
        TestdataValue s3 = new TestdataValue("s3");
        ValueSelector childValueSelector = SelectorTestUtils.mockValueSelector(variableDescriptor,
                s1, s2, s3);

        NullValueReinitializeVariableEntityFilter reinitializeVariableEntityFilter
                = new NullValueReinitializeVariableEntityFilter(childValueSelector.getVariableDescriptor());
        ValueSelector valueSelector = new ReinitializeVariableValueSelector(childValueSelector,
                reinitializeVariableEntityFilter);

        DefaultSolverScope solverScope = mock(DefaultSolverScope.class);
        valueSelector.solvingStarted(solverScope);

        AbstractSolverPhaseScope phaseScopeA = mock(AbstractSolverPhaseScope.class);
        when(phaseScopeA.getSolverScope()).thenReturn(solverScope);
        valueSelector.phaseStarted(phaseScopeA);

        AbstractStepScope stepScopeA1 = mock(AbstractStepScope.class);
        when(stepScopeA1.getPhaseScope()).thenReturn(phaseScopeA);
        valueSelector.stepStarted(stepScopeA1);
        e1.setPrimaryValue(p1);
        assertAllCodesOfValueSelectorForEntity(valueSelector, e1, "s1", "s2", "s3");
        valueSelector.stepEnded(stepScopeA1);

        AbstractStepScope stepScopeA2 = mock(AbstractStepScope.class);
        when(stepScopeA2.getPhaseScope()).thenReturn(phaseScopeA);
        valueSelector.stepStarted(stepScopeA2);
        e2.setSecondaryValue(s2);
        assertAllCodesOfValueSelectorForEntity(valueSelector, e2);
        valueSelector.stepEnded(stepScopeA2);

        valueSelector.phaseEnded(phaseScopeA);

        AbstractSolverPhaseScope phaseScopeB = mock(AbstractSolverPhaseScope.class);
        when(phaseScopeB.getSolverScope()).thenReturn(solverScope);
        valueSelector.phaseStarted(phaseScopeB);

        AbstractStepScope stepScopeB1 = mock(AbstractStepScope.class);
        when(stepScopeB1.getPhaseScope()).thenReturn(phaseScopeB);
        valueSelector.stepStarted(stepScopeB1);
        e2.setSecondaryValue(null);
        assertAllCodesOfValueSelectorForEntity(valueSelector, e2, "s1", "s2", "s3");
        valueSelector.stepEnded(stepScopeB1);


        AbstractStepScope stepScopeB2 = mock(AbstractStepScope.class);
        when(stepScopeB2.getPhaseScope()).thenReturn(phaseScopeB);
        valueSelector.stepStarted(stepScopeB2);
        e1.setPrimaryValue(null);
        assertAllCodesOfValueSelectorForEntity(valueSelector, e1, "s1", "s2", "s3");
        valueSelector.stepEnded(stepScopeB2);

        valueSelector.phaseEnded(phaseScopeB);

        valueSelector.solvingEnded(solverScope);

        verifySolverPhaseLifecycle(childValueSelector, 1, 2, 4);
        verify(childValueSelector, atMost(4)).iterator(any());
        verify(childValueSelector, atMost(4)).getSize(any());
    }

}