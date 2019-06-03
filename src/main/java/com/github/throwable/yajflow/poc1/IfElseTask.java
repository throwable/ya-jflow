package com.github.throwable.yajflow.poc1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;


class IfElseTask extends Task
{
	protected transient List<Task> tasks;
	protected List<Supplier<Boolean>> cond = new ArrayList<>();
	
	protected IfElseTask(Supplier<Boolean> cond, Task... tasks )
	{
	}

	protected IfElseTask(List<Supplier<Boolean>> cond, List<Task> tasks )
	{
	}


	public static class IfTask extends IfElseTask
	{
		public static class IfDef {
			protected List<Supplier<Boolean> > conds = new ArrayList<>();
			protected List<Task> tasks = new ArrayList<>();
			private Supplier<Boolean> cond;

			public IfDef(Supplier<Boolean> cond ) {
				this.cond = cond;
			}

			public IfDef( List<Supplier<Boolean>> conds, List<Task> tasks, Supplier<Boolean> cond )
			{
				this.conds = conds;
				this.tasks = tasks;
				this.cond = cond;
			}

			public IfTask Then( Task... tasks ) {
				this.conds.add( cond );
				//this.tasks.add( new SequenceTask( Arrays.asList( tasks ) ) );
				return new IfTask( this.conds, this.tasks );
			}
		}

		/*protected IfTask( Check cond, Task... tasks )
		{
			super( cond, tasks );
		}*/

		protected IfTask(List<Supplier<Boolean>> cond, List<Task> tasks )
		{
			super( cond, tasks );
		}
		
		
		public IfDef ElseIf(Supplier<Boolean> cond)
		{
			IfDef ifDef = new IfDef( this.cond, this.tasks, cond );
			return ifDef;
		}
		
		
		public IfElseTask Else(Task... tasks)
		{
			//this.tasks.add( new SequenceTask( Arrays.asList( tasks ) ) );
			return new IfElseTask(this.cond, Arrays.asList(tasks));
		}
	}
}
