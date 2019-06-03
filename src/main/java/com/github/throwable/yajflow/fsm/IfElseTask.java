package com.github.throwable.yajflow.fsm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class IfElseTask extends ComplexTask
{
	protected List<Check> cond = new ArrayList<Check>();
	
	protected IfElseTask( Check cond, Task... tasks )
	{
		super( Arrays.asList( tasks ) );
		this.cond.add( cond );
	}
	
	
	protected IfElseTask( List<Check> cond, List<Task> tasks )
	{
		super( tasks );
		this.cond = cond;
	}
	
	
	@Override
	protected int getNextTaskIdx( int taskIdx )
	{
		if ( taskIdx == -1 ) {
			taskIdx++;
			
			while ( taskIdx < cond.size() ) {
				if ( cond.get( taskIdx ).eval() )
					// found True condition
					return taskIdx;
				
				// next condition
				taskIdx++;
			}
			
			// not found: stay on Else Task or out of range (exit If)
			return taskIdx;
		} else
			// exit If
			return tasks.size();
	}
	
	
	public static class IfTask extends IfElseTask
	{
		public static class IfDef {
			protected List<Check> conds = new ArrayList<Check>();
			protected List<Task> tasks = new ArrayList<Task>();
			private Check cond;
			
			public IfDef( Check cond ) {
				this.cond = cond;
			}
			
			public IfDef( List<Check> conds, List<Task> tasks, Check cond )
			{
				this.conds = conds;
				this.tasks = tasks;
				this.cond = cond;
			}
			
			public IfTask Then( Task... tasks ) {
				this.conds.add( cond );
				this.tasks.add( new SequenceTask( Arrays.asList( tasks ) ) );
				return new IfTask( this.conds, this.tasks );
			}
		}
		
		/*protected IfTask( Check cond, Task... tasks )
		{
			super( cond, tasks );
		}*/
		
		protected IfTask( List<Check> cond, List<Task> tasks )
		{
			super( cond, tasks );
		}
		
		
		public IfDef ElseIf( Check cond )
		{
			IfDef ifDef = new IfDef( this.cond, this.tasks, cond );
			return ifDef;
		}
		
		
		public IfElseTask Else( Task... tasks )
		{
			this.tasks.add( new SequenceTask( Arrays.asList( tasks ) ) );
			return new IfElseTask( this.cond, this.tasks );
		}
	}
}
