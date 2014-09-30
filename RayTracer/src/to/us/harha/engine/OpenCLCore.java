package to.us.harha.engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opencl.CL;
import org.lwjgl.opencl.CL10;
import org.lwjgl.opencl.CLCommandQueue;
import org.lwjgl.opencl.CLContext;
import org.lwjgl.opencl.CLDevice;
import org.lwjgl.opencl.CLPlatform;

public class OpenCLCore {

	public static IntBuffer			m_errorBuff;
	public static CLPlatform		m_platform;
	public static List<CLDevice>	m_devices;
	public static CLContext			m_context;
	public static CLCommandQueue	m_queue;

	/*
	 * initOpenCL()
	 * Method to do all the initialization our OpenCL context needs
	 */
	public static void initOpenCL() {
		try {
			// First, initialize OpenCL
			CL.create();
			// Initialize the m_errorBuff
			m_errorBuff = BufferUtils.createIntBuffer(1);
			// Get the main OpenCL platform which resides in ID 0
			m_platform = CLPlatform.getPlatforms().get(0);
			// Get the OpenCL devices
			m_devices = m_platform.getDevices(CL10.CL_DEVICE_TYPE_GPU);
			// Get the CLContext, it ties the devices, kernels, memory and queues
			m_context = CLContext.create(m_platform, m_devices, m_errorBuff);
			// Initialize the command queue
			m_queue = CL10.clCreateCommandQueue(m_context, m_devices.get(0), CL10.CL_QUEUE_PROFILING_ENABLE, m_errorBuff);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		System.out.println("OpenCL initialized... " + m_context.isValid());
	}

	/*
	 * destroyOpenCL()
	 * Method to destroy the OpenCL context to free memory
	 */
	public static void destroyOpenCL() {
		CL10.clReleaseCommandQueue(m_queue);
		CL10.clReleaseContext(m_context);
		CL.destroy();
		System.out.println("OpenCL destroyed... ");
	}

	public static String loadCLSourceFile(String path) {
		if (!path.endsWith(".cls")) {
			path += ".cls";
		}
		String workingDir = System.getProperty("user.dir");
		String finalPath = workingDir + "\\" + path;
		String line;
		StringBuilder result = new StringBuilder();
		String resultString = null;
		BufferedReader reader = null;
		try {
			File clSourceFile = new File(finalPath);
			reader = new BufferedReader(new FileReader(clSourceFile));
			while ((line = reader.readLine()) != null) {
				result.append(line);
				result.append("\n");
			}
			resultString = result.toString();
		} catch (Exception e) {
			System.err.println("Error loading the OpenCL source file: " + finalPath);
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				System.err.println("Error closing the OpenCL source file: " + finalPath);
				e.printStackTrace();
			}
		}
		return resultString;
	}

}
