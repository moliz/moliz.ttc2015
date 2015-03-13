/*
* generated by Xtext
*/
package org.modelexecution.operationalsemantics.ui.contentassist.antlr;

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.RecognitionException;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ui.editor.contentassist.antlr.AbstractContentAssistParser;
import org.eclipse.xtext.ui.editor.contentassist.antlr.FollowElement;
import org.eclipse.xtext.ui.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;

import com.google.inject.Inject;

import org.modelexecution.operationalsemantics.services.ActivityDiagramInputGrammarAccess;

public class ActivityDiagramInputParser extends AbstractContentAssistParser {
	
	@Inject
	private ActivityDiagramInputGrammarAccess grammarAccess;
	
	private Map<AbstractElement, String> nameMappings;
	
	@Override
	protected org.modelexecution.operationalsemantics.ui.contentassist.antlr.internal.InternalActivityDiagramInputParser createParser() {
		org.modelexecution.operationalsemantics.ui.contentassist.antlr.internal.InternalActivityDiagramInputParser result = new org.modelexecution.operationalsemantics.ui.contentassist.antlr.internal.InternalActivityDiagramInputParser(null);
		result.setGrammarAccess(grammarAccess);
		return result;
	}
	
	@Override
	protected String getRuleName(AbstractElement element) {
		if (nameMappings == null) {
			nameMappings = new HashMap<AbstractElement, String>() {
				private static final long serialVersionUID = 1L;
				{
					put(grammarAccess.getValueAccess().getAlternatives(), "rule__Value__Alternatives");
					put(grammarAccess.getEBooleanAccess().getAlternatives(), "rule__EBoolean__Alternatives");
					put(grammarAccess.getEStringAccess().getAlternatives(), "rule__EString__Alternatives");
					put(grammarAccess.getInputAccess().getGroup(), "rule__Input__Group__0");
					put(grammarAccess.getInputAccess().getGroup_2(), "rule__Input__Group_2__0");
					put(grammarAccess.getInputValueAccess().getGroup(), "rule__InputValue__Group__0");
					put(grammarAccess.getEIntAccess().getGroup(), "rule__EInt__Group__0");
					put(grammarAccess.getInputAccess().getInputValuesAssignment_1(), "rule__Input__InputValuesAssignment_1");
					put(grammarAccess.getInputAccess().getInputValuesAssignment_2_1(), "rule__Input__InputValuesAssignment_2_1");
					put(grammarAccess.getInputValueAccess().getVariableAssignment_0(), "rule__InputValue__VariableAssignment_0");
					put(grammarAccess.getInputValueAccess().getValueAssignment_2(), "rule__InputValue__ValueAssignment_2");
					put(grammarAccess.getBooleanValueAccess().getValueAssignment(), "rule__BooleanValue__ValueAssignment");
					put(grammarAccess.getIntegerValueAccess().getValueAssignment(), "rule__IntegerValue__ValueAssignment");
				}
			};
		}
		return nameMappings.get(element);
	}
	
	@Override
	protected Collection<FollowElement> getFollowElements(AbstractInternalContentAssistParser parser) {
		try {
			org.modelexecution.operationalsemantics.ui.contentassist.antlr.internal.InternalActivityDiagramInputParser typedParser = (org.modelexecution.operationalsemantics.ui.contentassist.antlr.internal.InternalActivityDiagramInputParser) parser;
			typedParser.entryRuleInput();
			return typedParser.getFollowElements();
		} catch(RecognitionException ex) {
			throw new RuntimeException(ex);
		}		
	}
	
	@Override
	protected String[] getInitialHiddenTokens() {
		return new String[] { "RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT" };
	}
	
	public ActivityDiagramInputGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(ActivityDiagramInputGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}
