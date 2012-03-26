package arden2bytecodeeclipseplugin.launchconfig;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.IVMRunner;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jdt.launching.VMRunnerConfiguration;

import arden2bytecodeeclipseplugin.Activator;

public class Arden2ByteCodeLaunchConfiguration extends LaunchConfigurationDelegate {

	public final static String MLM_EXTENSION = "mlm"; //$NON-NLS-1$
	
	private String[] computeClasspath(ILaunchConfiguration config) throws CoreException {
		IRuntimeClasspathEntry[] entries = JavaRuntime
				.computeUnresolvedRuntimeClasspath(config);
		entries = JavaRuntime.resolveRuntimeClasspath(entries, config);
		List<String> userEntries = new ArrayList<String>(entries.length);
		Set<String> set = new HashSet<String>(entries.length);
				
		URL compilerUrl = Platform.getBundle("Arden2ByteCodeCompiler").getEntry("/bin/"); //$NON-NLS-1$ $NON-NLS-2$
		URL jewelCliUrl = Platform.getBundle("Arden2ByteCodeCompiler").getEntry("/jewelcli/"); //$NON-NLS-1$ $NON-NLS-2$
		String compilerCp = null;
		String jewelCliCp = null;
		try {
			compilerCp = FileLocator.toFileURL(compilerUrl).getFile();
			jewelCliCp = FileLocator.toFileURL(jewelCliUrl).getFile();
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, 
					Activator.PLUGIN_ID, 
					0,
					"Error locating JAR",
					e));
		}
		userEntries.add(compilerCp);
		set.add(compilerCp);
		userEntries.add(jewelCliCp);
		set.add(jewelCliCp);
		
		for (int i = 0; i < entries.length; i++) {
			if (entries[i].getClasspathProperty() != IRuntimeClasspathEntry.BOOTSTRAP_CLASSES) {
				String location = entries[i].getLocation();
				if (location != null) {
					if (!set.contains(location)) {
						userEntries.add(location);
						set.add(location);
					}
				}
			}
		}
		return (String[]) userEntries.toArray(new String[userEntries.size()]);
	}
	
	@Override
	public void launch(ILaunchConfiguration config, String mode, ILaunch launch,
			IProgressMonitor monitor) throws CoreException {
		try {
			String additionalCompilerArguments = config.getAttribute(
					MainTab.COMPILER_ARGS, 
					MainTab.COMPILER_ARGS_DEFAULT);  
			
			List<String> args = new LinkedList<String>();
			boolean logo = config.getAttribute(MainTab.LOGO, MainTab.LOGO_DEFAULT);
			if (!logo) {
				args.add("--nologo"); //$NON-NLS-1$
			}
			args.add("-r"); //$NON-NLS-1$
			String projName = config.getAttribute(MainTab.PROJECT, MainTab.PROJECT_DEFAULT).trim();
			IProject proj = ResourcesPlugin.getWorkspace().getRoot().getProject(projName);
			if (proj == null) {
				monitor.setCanceled(true);
				throw new CoreException(new Status(Status.ERROR, 
						Activator.PLUGIN_ID, 
						"Could not find that project."));
			}
			String mlmPath = config.getAttribute(MainTab.MLM, MainTab.MLM_DEFAULT);
			IResource mlm = proj.findMember(mlmPath);
			if (mlm == null) {
				monitor.setCanceled(true);
				throw new CoreException(new Status(Status.ERROR, 
						Activator.PLUGIN_ID, 
						"Could not find that MLM in the project."));
			}
			IPath mlmLoc = mlm.getLocation();
			if (mlmLoc == null) {
				monitor.setCanceled(true);
				throw new CoreException(new Status(Status.ERROR, 
						Activator.PLUGIN_ID, 
						"Could not find that MLM in the file system."));
			}
			args.add(mlmLoc.toOSString());
			
			Pattern argPattern = Pattern.compile("\\\"[^\\\"]*\\\"|\\'[^\\']*\\'|\\S+");  //$NON-NLS-1$
			Matcher m = argPattern.matcher(additionalCompilerArguments);
			while (m.find()) {
				args.add(m.group());
			}
			
			monitor.beginTask("Running MLM...", 4);
			
			String[] classPath = computeClasspath(config);
			
			VMRunnerConfiguration vmConfig = 
				new VMRunnerConfiguration(
						"arden.MainClass",  //$NON-NLS-1$ 
						classPath);
			vmConfig.setProgramArguments(args.toArray(new String[args.size()]));
			vmConfig.setWorkingDirectory(".");			

			monitor.worked(1);

			IVMInstall defaultVM = JavaRuntime.getDefaultVMInstall();
			IVMRunner runner = defaultVM.getVMRunner(mode);
			if (runner != null) {
				runner.run(vmConfig, launch, monitor);
			} else {
				monitor.setCanceled(true);
			}
			monitor.done();
		} catch (final CoreException e) {
			monitor.setCanceled(true);
			throw e;
		}
	}

}
