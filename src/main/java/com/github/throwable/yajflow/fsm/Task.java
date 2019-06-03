package com.github.throwable.yajflow.fsm;



public abstract class Task
{
	private String name;
	//private transient ComplexTask parent;
	
	/**
	 * Visitor: when a task is in inserted into parent container.
	 * This controls twice insertion of task.
	 * @param parentTask
	 */
	/*protected void addNotify( ComplexTask parentTask )
	{
		if ( parent == null )
			parent = parentTask;
		else throw new IllegalStateException( "Task instance was used twice" );
	}*/
	
	/**
	 * Todo: add ExecutionContext (TransactionContext) instead of programCounter
	 * @param programCounter
	 * @return true if process execution is continued,
	 * 			false to break until next Process.execute() call. The execution will
	 * 			be continued from the next task.
	 */
	protected boolean execute( ExecutionContext execCtx, int currDeepLevel ) 
	{
		return true;
	}
	
	public void name( String name )
	{
		this.name = name;
	}
	
	public String name()
	{
		return name;
	}
	
	
	@Override 
	public String toString() {
		if ( name != null )
			return name;
		else return super.toString();
	}
}
