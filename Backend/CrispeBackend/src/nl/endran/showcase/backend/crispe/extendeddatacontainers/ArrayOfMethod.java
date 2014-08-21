package nl.endran.showcase.backend.crispe.extendeddatacontainers;

import java.util.ArrayList;

import nl.endran.showcaselib.datacontainers.Method;

public class ArrayOfMethod implements IArrayOf<Method> {
	private final ArrayList<Method> methodList = new ArrayList<Method>();

	public ArrayList<Method> getList() {
		return methodList;
	}

	public void setMethod(final Method method) {
		methodList.add(method);
	}
}
