package com.github.throwable.yajflow.fsm;

import java.util.List;

/**
 * Sequence task executes subtasks sequentially
 * 
 * @author akuranov
 */
public class SequenceTask extends ComplexTask
{
	public SequenceTask( List<Task> tasks ) {
		super( tasks );
	}
	
	@Override
	protected int getNextTaskIdx( int taskIdx ) {
		return taskIdx+1;
	}
}
