/*
* generated by Xtext
*/
package org.modelexecution.operationalsemantics.parser.antlr;

import java.io.InputStream;
import org.eclipse.xtext.parser.antlr.IAntlrTokenFileProvider;

public class ActivityDiagramInputAntlrTokenFileProvider implements IAntlrTokenFileProvider {
	
	public InputStream getAntlrTokenFile() {
		ClassLoader classLoader = getClass().getClassLoader();
    	return classLoader.getResourceAsStream("org/modelexecution/operationalsemantics/parser/antlr/internal/InternalActivityDiagramInput.tokens");
	}
}
