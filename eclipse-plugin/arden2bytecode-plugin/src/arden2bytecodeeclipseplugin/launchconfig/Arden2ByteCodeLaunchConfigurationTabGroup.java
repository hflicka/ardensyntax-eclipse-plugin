package arden2bytecodeeclipseplugin.launchconfig;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

public class Arden2ByteCodeLaunchConfigurationTabGroup extends
		AbstractLaunchConfigurationTabGroup {

	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		ILaunchConfigurationTab[] tabs = null;
		tabs = new ILaunchConfigurationTab[] {
				new MainTab()
		};
		setTabs(tabs);
	}

}
