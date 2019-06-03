package com.github.throwable.yajflow.fsm;

import java.util.Arrays;
import java.util.List;


/**
 * While task defines a simple loop driven by condition
 * 
 * @author akuranov
 */
class WhileTask extends ComplexTask
{
	private Check cond;
	
	public static class WhileDef {
		private Check cond;
		
		public WhileDef( Check cond ) {
			this.cond = cond;
		}
		
		public WhileTask Do( Task... tasks ) {
			return new WhileTask( cond, Arrays.asList( tasks ) );
		}
	}
	
	protected WhileTask( Check cond, List<Task> tasks )
	{
		super( tasks );
		this.cond = cond;
	}
	
	@Override
	protected int getNextTaskIdx( int taskIdx )
	{
		if ( taskIdx == -1 )
			return cond.eval() ? 0 : tasks.size();
		else {
			taskIdx++;
			
			if ( taskIdx < tasks.size() )
				return taskIdx;
			else
				return cond.eval() ? 0 : tasks.size();
		}
	}
}
