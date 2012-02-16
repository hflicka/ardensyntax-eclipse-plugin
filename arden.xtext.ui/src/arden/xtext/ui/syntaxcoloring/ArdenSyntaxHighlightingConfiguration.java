package arden.xtext.ui.syntaxcoloring;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

public class ArdenSyntaxHighlightingConfiguration implements
		IHighlightingConfiguration {

	public static final String DEFAULTHL = "defaultHighlighting";

	public void configure(IHighlightingConfigurationAcceptor acceptor) {
		acceptor.acceptDefaultHighlighting(DEFAULTHL, "Default Highlighting",
				defaultHighlighting());
	}

	public TextStyle defaultHighlighting() {
		TextStyle textStyle = new TextStyle();
		//textStyle.setBackgroundColor(new RGB(127, 255, 255));
		textStyle.setColor(new RGB(127, 0, 85));
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}
}
