package arden2bytecodeeclipseplugin.breakpoints;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.ILineBreakpoint;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.texteditor.ITextEditor;

import arden2bytecodeeclipseplugin.Activator;

public class BreakpointAdapter implements
		IToggleBreakpointsTarget {

	public BreakpointAdapter() {
		
	}
	
	@Override
	public void toggleLineBreakpoints(IWorkbenchPart part, ISelection iselection)
			throws CoreException {
		ITextEditor editor = ToggleBreakpointsTarget.getEditor(part);
		if (editor != null) {
			IResource resource = (IResource) editor.getEditorInput().getAdapter(IResource.class);
			ITextSelection selection = (ITextSelection) iselection;
			int line = selection.getStartLine();
			IBreakpoint[] breakpoints = DebugPlugin.getDefault().getBreakpointManager().getBreakpoints(ArdenSyntaxLineBreakpoint.MODELIDENTIFIER);
			for (IBreakpoint breakpoint : breakpoints) {
				if (resource.equals(breakpoint.getMarker().getResource())) {
					if (((ILineBreakpoint)breakpoint).getLineNumber() == line + 1) {
						breakpoint.delete();
						return;
					}
				}
			}
			ArdenSyntaxLineBreakpoint linebreakpoint = new ArdenSyntaxLineBreakpoint(resource, line + 1);
			DebugPlugin.getDefault().getBreakpointManager().addBreakpoint(linebreakpoint);
		}
	}
	
	@Override
	public boolean canToggleLineBreakpoints(IWorkbenchPart part,
			ISelection selection) {
		return ToggleBreakpointsTarget.getEditor(part) != null;
	}

	@Override
	public void toggleMethodBreakpoints(IWorkbenchPart part,
			ISelection selection) throws CoreException {

	}

	@Override
	public boolean canToggleMethodBreakpoints(IWorkbenchPart part,
			ISelection selection) {
		return false;
	}

	@Override
	public void toggleWatchpoints(IWorkbenchPart part, ISelection selection)
			throws CoreException {

	}

	@Override
	public boolean canToggleWatchpoints(IWorkbenchPart part,
			ISelection selection) {
		return false;
	}

}
