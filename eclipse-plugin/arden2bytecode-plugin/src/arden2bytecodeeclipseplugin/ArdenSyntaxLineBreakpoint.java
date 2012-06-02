package arden2bytecodeeclipseplugin;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.Breakpoint;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.LineBreakpoint;

public class ArdenSyntaxLineBreakpoint extends LineBreakpoint {

	public final static String MODELIDENTIFIER = Activator.PLUGIN_ID;
	
	public ArdenSyntaxLineBreakpoint() {
		super();
	}
	
	public ArdenSyntaxLineBreakpoint(final IResource resource, final int line) throws CoreException {
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			@Override
			public void run(IProgressMonitor monitor) throws CoreException {
				IMarker marker = resource.createMarker(Activator.PLUGIN_ID + ".lineBreakpoint.marker");
				setMarker(marker);
				setEnabled(true);
				ensureMarker().setAttribute(IMarker.LINE_NUMBER, line);
				ensureMarker().setAttribute(IBreakpoint.ID, getModelIdentifier());
				ensureMarker().setAttribute(IMarker.MESSAGE, "Line Breakpoint: " 
						+ resource.getName() + "[line: " + line + "]");
			}
		};
		run(getMarkerRule(resource), runnable);
	}

	@Override
	public String getModelIdentifier() {
		return MODELIDENTIFIER;
	}

}
