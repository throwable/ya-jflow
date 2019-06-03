package com.github.throwable.yajflow.fsm;

import java.util.List;


/**
 * Abstract task that consists of subtasks with custom driven execution order.
 * 
 * @author akuranov
 */
public abstract class ComplexTask extends Task
{
	protected transient List<Task> tasks;
	
	
	public ComplexTask( List<Task> tasks )
	{
		this.tasks = tasks;
		/*
		for( Task t : tasks )
			t.addNotify( this );*/
	}
	
	
	/**
	 * pcStack[]=1, deep=0
	 * @param pcStack
	 * @param deep
	 */
	@Override
	protected boolean execute( ExecutionContext execCtx, int currentDeepLevel ) 
	{
		int taskIdx;
		
		if ( currentDeepLevel <= execCtx.execPointer.size()-1 ) {
			taskIdx = execCtx.execPointer.get( currentDeepLevel );
			
			// if we reach wait task we move to the next 
			if ( currentDeepLevel == execCtx.execPointer.size()-1 ) {
				execCtx.execPointer.pop();
				taskIdx = getNextTaskIdx( taskIdx );
				execCtx.execPointer.push( taskIdx );
			}
		} else {
			taskIdx = getNextTaskIdx( -1 );
			execCtx.execPointer.push( taskIdx );
		}
		
		while ( 0 <= taskIdx && taskIdx < tasks.size() ) {
			Task t = tasks.get( taskIdx );
			
			if ( !t.execute( execCtx, currentDeepLevel+1 ) )
				/* asynchronous continuations */
				return false;
			
			execCtx.execPointer.pop();
			taskIdx = getNextTaskIdx( taskIdx );
			execCtx.execPointer.push( taskIdx );
		}
		
		execCtx.execPointer.pop();
		return true;
	}
	
	/**
	 * Returns an index of next task.
	 * @param taskIdx: current task done. Value -1 means that the first (initial)
	 * 		task must be returned. 
	 * @return new task index or out of range index if complex task was terminated
	 */
	protected abstract int getNextTaskIdx( int taskIdx );
}
