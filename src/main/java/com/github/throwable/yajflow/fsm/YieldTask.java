package com.github.throwable.yajflow.fsm;


/**
 * Task breaks the execution of process until next Process.exec() will be called
 * 
 * @author akuranov
 */
public class YieldTask extends Task
{
	@Override
	protected boolean execute( ExecutionContext execCtx, int currDeepLevel )
	{ 
		return false;
	}
}
