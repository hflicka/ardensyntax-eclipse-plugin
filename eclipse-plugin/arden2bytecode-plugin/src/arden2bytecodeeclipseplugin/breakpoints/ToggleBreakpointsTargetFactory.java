package arden2bytecodeeclipseplugin.breakpoints;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTargetFactory;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.texteditor.ITextEditor;

import arden2bytecodeeclipseplugin.Activator;

public class ToggleBreakpointsTargetFactory implements
		IToggleBreakpointsTargetFactory {

	public static final String TARGET_ID = Activator.PLUGIN_ID + ".breakpoints.ToggleBreakpointsTargetFactory";
	
	public static Set TOGGLE_IDS = new HashSet(1);
	
	static {
		TOGGLE_IDS.add(TARGET_ID);
	}
	
	public static IToggleBreakpointsTarget TARGET = new ToggleBreakpointsTarget();
	
	public ToggleBreakpointsTargetFactory() {
		
	}

	@Override
	public Set getToggleTargets(IWorkbenchPart part, ISelection selection) {
		if (part instanceof ITextEditor) {
			return TOGGLE_IDS;
		}
		return Collections.emptySet();
	}

	@Override
	public String getDefaultToggleTarget(IWorkbenchPart part,
			ISelection selection) {
		ITextEditor editor = ToggleBreakpointsTarget.getEditor(part);
		if (editor != null) {
			return TARGET_ID;
		}
		return null;
	}

	@Override
	public IToggleBreakpointsTarget createToggleTarget(String targetID) {
		if (TARGET_ID.equals(targetID)) {
			return TARGET;
		}
		return null;
	}

	@Override
	public String getToggleTargetName(String targetID) {
		return "toggletargetname";
	}

	@Override
	public String getToggleTargetDescription(String targetID) {
		// TODO Auto-generated method stub
		return "toggletargetdescription";
	}

}
