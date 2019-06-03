package com.github.throwable.yajflow.fsm;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


class ExecutionContext
{
	public Stack<Integer> execPointer;
	public Object input;		// message for InputTask
	public List<Object> output = new ArrayList<Object>();		// resulting message (last ActionTask)
}
