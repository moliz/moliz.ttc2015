package org.modelexecution.operationalsemantics.ad.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.modelexecution.operationalsemantics.ActivityDiagramInputStandaloneSetup;
import org.modelexecution.operationalsemantics.ActivityDiagramStandaloneSetup;

import activitydiagram.Activity;
import activitydiagram.ActivityNode;
import activitydiagram.BooleanValue;
import activitydiagram.Input;
import activitydiagram.InputValue;
import activitydiagram.IntegerValue;
import activitydiagram.Trace;
import activitydiagram.Value;
import activitydiagram.Variable;

public class TestSuite {

	private XtextResourceSet resourceSet = null;

	@Before
	public void initializeResourceSet() {
		resourceSet = new XtextResourceSet();
	}

	@Before
	public void setupGrammar() {
		ActivityDiagramStandaloneSetup.doSetup();
		ActivityDiagramInputStandaloneSetup.doSetup();
	}

	@Test
	public void test1() {
		Trace trace = executeActivity("model/test1.ad");
		assertTrue(checkTotalExecutionOrder(trace, "initialNode1", "action1",
				"finalNode1"));
	}

	@Test
	public void test2() {
		Trace trace = executeActivity("model/test2.ad");
		assertTrue(checkPartialExecutionOrder(trace, "initialNode2",
				"forkNode1", "action2", "joinNode1", "finalNode2"));
		assertTrue(checkPartialExecutionOrder(trace, "initialNode2",
				"forkNode1", "action3", "joinNode1", "finalNode2"));
	}

	@Test
	public void test3() {
		Trace trace = executeActivity("model/test3.ad");
		assertTrue(checkTotalExecutionOrder(trace, "initialNode3",
				"decisionNode1", "action4", "mergeNode1", "finalNode3"));
		assertFalse(checkNodeExecuted(trace, "action5"));
	}

	@Test
	public void test4() {
		Trace trace = executeActivity("model/test4.ad");
		assertTrue(checkTotalExecutionOrder(trace, "initialNode4", "action6",
				"action7", "action8", "action9", "finalNode4"));
		assertEquals(3, getIntegerVariableValue(trace, "var3"));
		assertEquals(1, getIntegerVariableValue(trace, "var4"));
		assertEquals(2, getIntegerVariableValue(trace, "var5"));
		assertTrue(getBooleanVariableValue(trace, "var6"));
		assertFalse(getBooleanVariableValue(trace, "var7"));
		assertTrue(getBooleanVariableValue(trace, "var8"));
	}

	@Test
	public void test5() {
		Trace trace = executeActivity("model/test5.ad", "model/test5.adinput");
		assertTrue(checkTotalExecutionOrder(trace, "initialNode5", "action10",
				"finalNode5"));
		assertEquals(10, getIntegerVariableValue(trace, "var9"));
		assertEquals(5, getIntegerVariableValue(trace, "var10"));
		assertEquals(15, getIntegerVariableValue(trace, "var11"));
	}

	@Test
	public void test6_3() {
		Trace trace = executeActivity("model/test6.ad", "model/test6_3.adinput");
		assertTrue(checkTotalExecutionOrder(trace, "initialNode6", "a", "b",
				"c", "d", "mergeE", "e", "decisionI", "f", "mergeFinal6",
				"finalNode6"));
		assertFalse(checkNodeExecuted(trace, "g"));
		assertFalse(checkNodeExecuted(trace, "h"));
		assertFalse(checkNodeExecuted(trace, "i"));
		assertFalse(checkNodeExecuted(trace, "j"));
		assertFalse(checkNodeExecuted(trace, "k"));
		assertFalse(checkNodeExecuted(trace, "l"));
		assertFalse(checkNodeExecuted(trace, "decisionLoop"));

		assertEquals(0, getIntegerVariableValue(trace, "loop"));
	}

	@Test
	public void test6_2() {
		Trace trace = executeActivity("model/test6.ad", "model/test6_2.adinput");
		assertTrue(checkTotalExecutionOrder(trace, "initialNode6", "a", "b",
				"c", "d", "mergeE", "e", "decisionI", "g", "h", "mergeFinal6",
				"finalNode6"));
		assertFalse(checkNodeExecuted(trace, "f"));
		assertFalse(checkNodeExecuted(trace, "i"));
		assertFalse(checkNodeExecuted(trace, "j"));
		assertFalse(checkNodeExecuted(trace, "k"));
		assertFalse(checkNodeExecuted(trace, "l"));
		assertFalse(checkNodeExecuted(trace, "decisionLoop"));

		assertEquals(0, getIntegerVariableValue(trace, "loop"));
	}

	@Test
	public void test6_1() {
		Trace trace = executeActivity("model/test6.ad", "model/test6_1.adinput");

		String[] expectedExecutionOrder = new String[105];
		int i = 0;
		expectedExecutionOrder[i++] = "initialNode6";
		expectedExecutionOrder[i++] = "a";
		expectedExecutionOrder[i++] = "b";
		expectedExecutionOrder[i++] = "c";
		expectedExecutionOrder[i++] = "d";
		for (int j = 0; j < 13; ++j) {
			expectedExecutionOrder[i++] = "mergeE";
			expectedExecutionOrder[i++] = "e";
			expectedExecutionOrder[i++] = "decisionI";
			expectedExecutionOrder[i++] = "i";
			expectedExecutionOrder[i++] = "j";
			expectedExecutionOrder[i++] = "decisionLoop";
			expectedExecutionOrder[i++] = "l";
		}
		expectedExecutionOrder[i++] = "mergeE";
		expectedExecutionOrder[i++] = "e";
		expectedExecutionOrder[i++] = "decisionI";
		expectedExecutionOrder[i++] = "i";
		expectedExecutionOrder[i++] = "j";
		expectedExecutionOrder[i++] = "decisionLoop";
		expectedExecutionOrder[i++] = "k";
		expectedExecutionOrder[i++] = "mergeFinal6";
		expectedExecutionOrder[i++] = "finalNode6";

		assertTrue(checkTotalExecutionOrder(trace, expectedExecutionOrder));
		assertFalse(checkNodeExecuted(trace, "f"));
		assertFalse(checkNodeExecuted(trace, "g"));
		assertFalse(checkNodeExecuted(trace, "h"));

		assertEquals(13, getIntegerVariableValue(trace, "loop"));
	}
	
	@Test
	public void test7_false() {
		Trace trace = executeActivity("model/test7.ad", "model/test7_false.adinput");
		assertTrue(checkTotalExecutionOrder(trace, "initialNode7", "register", "decisionInternal",
				"assignToProjectExternal", "mergeAuthorizePayment", "authorizePayment", "finalNode7"));
		assertFalse(checkNodeExecuted(trace, "getWelcomePackage"));
		assertFalse(checkNodeExecuted(trace, "forkGetWelcomePackage"));
		assertFalse(checkNodeExecuted(trace, "assignToProject"));
		assertFalse(checkNodeExecuted(trace, "addToWebsite"));
		assertFalse(checkNodeExecuted(trace, "joinManagerInterview"));
		assertFalse(checkNodeExecuted(trace, "managerInterview"));
		assertFalse(checkNodeExecuted(trace, "managerReport"));
	}
	
	@Test
	public void test7_true() {
		Trace trace = executeActivity("model/test7.ad", "model/test7_true.adinput");
		assertTrue(checkPartialExecutionOrder(trace, "initialNode7", "register", "decisionInternal",
				"getWelcomePackage", "forkGetWelcomePackage", "joinManagerInterview", "managerInterview", "managerReport",
				"mergeAuthorizePayment", "authorizePayment", "finalNode7"));
		assertTrue(checkPartialExecutionOrder(trace, "forkGetWelcomePackage", "assignToProject", "joinManagerInterview"));
		assertTrue(checkPartialExecutionOrder(trace, "forkGetWelcomePackage", "addToWebsite", "joinManagerInterview"));
		
		
		assertFalse(checkNodeExecuted(trace, "assignToProjectExternal"));
	}

	private Trace executeActivity(String modelPath) {
		return executeActivity(modelPath, null);
	}

	private Trace executeActivity(String modelPath, String inputPath) {
		Activity activity = getActivity(modelPath);
		List<InputValue> inputValues = getInputValues(inputPath);
		activity.main(inputValues);
		Trace trace = activity.getTrace();
		return trace;
	}

	private Activity getActivity(String modelPath) {
		Resource resource = resourceSet.getResource(createFileURI(modelPath),
				true);
		EObject eObject = resource.getContents().get(0);
		if (eObject instanceof Activity) {
			Activity activity = (Activity) eObject;
			return activity;
		}
		return null;
	}

	private List<InputValue> getInputValues(String inputPath) {
		List<InputValue> inputValues = new ArrayList<InputValue>();
		if (inputPath != null) {
			Resource resource = resourceSet.getResource(
					createFileURI(inputPath), true);
			EObject eObject = resource.getContents().get(0);
			if (eObject instanceof Input) {
				Input input = (Input) eObject;
				inputValues.addAll(input.getInputValues());
			}
		}
		return inputValues;
	}

	private URI createFileURI(String path) {
		return URI.createFileURI(createFile(path).getAbsolutePath());
	}

	private File createFile(String path) {
		return new File(path);
	}

	private boolean checkTotalExecutionOrder(Trace trace,
			String... activityNodeNames) {
		boolean result = true;
		if (trace.getExecutedNodes().size() != activityNodeNames.length) {
			result = false;
		} else {
			for (int i = 0; i < trace.getExecutedNodes().size(); ++i) {
				ActivityNode activityNode = trace.getExecutedNodes().get(i);
				String activityNodeName = activityNode.getName();
				String expectedActivityNodeName = activityNodeNames[i];
				if (!activityNodeName.equals(expectedActivityNodeName)) {
					result = false;
				}
			}
		}
		return result;
	}

	private boolean checkPartialExecutionOrder(Trace trace,
			String... activityNodeNames) {
		int[] orderIndexes = new int[activityNodeNames.length];
		for (int i = 0; i < activityNodeNames.length; ++i) {
			orderIndexes[i] = getFirstOrderIndex(trace, activityNodeNames[i]);
		}
		for (int i = 0; i < activityNodeNames.length - 1; ++i) {
			int index1 = orderIndexes[i];
			int index2 = orderIndexes[i + 1];
			if (!(index1 < index2)) {
				return false;
			}
		}
		return true;
	}

	private int getFirstOrderIndex(Trace trace, String activityNodeName) {
		for (int i = 0; i < trace.getExecutedNodes().size(); ++i) {
			ActivityNode node = trace.getExecutedNodes().get(i);
			if (node.getName().equals(activityNodeName)) {
				return i;
			}
		}
		return -1;
	}

	private boolean checkNodeExecuted(Trace trace, String string) {
		int orderIndex = getFirstOrderIndex(trace, string);
		return orderIndex != -1;
	}

	private int getIntegerVariableValue(Trace trace, String variableName) {
		Value currentValue = getVariableValue(trace, variableName);
		if (currentValue instanceof IntegerValue) {
			IntegerValue integerValue = (IntegerValue) currentValue;
			return integerValue.getValue();
		} else {
			if (currentValue == null)
				Assert.fail("No value found");
			else
				Assert.fail("Value of wrong type");
		}
		return -1;
	}

	private boolean getBooleanVariableValue(Trace trace, String variableName) {
		Value currentValue = getVariableValue(trace, variableName);
		if (currentValue instanceof BooleanValue) {
			BooleanValue booleanValue = (BooleanValue) currentValue;
			return booleanValue.isValue();
		} else {
			if (currentValue == null)
				Assert.fail("No value found");
			else
				Assert.fail("Value of wrong type");
		}
		return false;
	}

	private Value getVariableValue(Trace trace, String variableName) {
		Activity activity = getActivity(trace);
		Variable variable = getVariable(activity, variableName);
		Value currentValue = variable.getCurrentValue();
		return currentValue;
	}

	private Variable getVariable(Activity activity, String variableName) {
		List<Variable> allVariables = new ArrayList<Variable>();
		allVariables.addAll(activity.getLocals());
		allVariables.addAll(activity.getInputs());
		for (Variable var : allVariables) {
			if (var.getName().equals(variableName)) {
				return var;
			}
		}
		return null;
	}

	private Activity getActivity(Trace trace) {
		Activity activity = (Activity) trace.eContainer();
		return activity;
	}

	// private boolean checkExecutionOrderOfTwoNodes(Trace trace, String
	// predecessorActivityNodeName, String successorActivityNodeName) {
	// int predecessorOrderIndex = -1;
	// int successorOrderIndex = -1;
	// for(int i=0;i<trace.getExecutedNodes().size();++i) {
	// ActivityNode node = trace.getExecutedNodes().get(i);
	// if (node.getName().equals(predecessorActivityNodeName)) {
	// predecessorOrderIndex = i;
	// }
	// if (node.getName().equals(successorActivityNodeName)) {
	// successorOrderIndex = i;
	// }
	// }
	// if (predecessorOrderIndex == -1 || successorOrderIndex == -1) {
	// return false;
	// } else {
	// return predecessorOrderIndex < successorOrderIndex;
	// }
	// }
}
