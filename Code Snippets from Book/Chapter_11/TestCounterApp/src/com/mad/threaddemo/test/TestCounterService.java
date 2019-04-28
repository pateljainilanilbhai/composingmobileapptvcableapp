package com.mad.threaddemo.test;

import com.mad.threaddemo.CounterService;

import android.content.Intent;
import android.test.ServiceTestCase;

public class TestCounterService extends ServiceTestCase<CounterService> {

	private Intent intent;
	private CounterService service;

	public TestCounterService() {
		super(CounterService.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		intent = new Intent(getContext(), CounterService.class);

	}

	protected void tearDown() throws Exception {
		super.tearDown();
		intent = null;
		service = null;
	}

	public void testServiceStarts() {
		startService(intent);
		service = getService();
		assertEquals(true, service.mServiceCreated);
		assertEquals(true, service.mServiceStarted);
	}

	public void testServiceStops() {
		startService(intent);
		shutdownService();
		service = getService();
		assertEquals(false, service.mServiceCreated);
		assertEquals(false, service.mServiceStarted);
		assertEquals(false, service.mServiceBound);
	}

	public void testMutipleStarts() {
		startService(intent);
		startService(intent);
		startService(intent);
		bindService(intent);
		bindService(intent);
		service = getService();
		assertEquals(1, service.mServiceCreationCount);
	}

}
