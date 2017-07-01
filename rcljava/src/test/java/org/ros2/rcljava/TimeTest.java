/* Copyright 2017 Esteve Fernandez <esteve@apache.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ros2.rcljava;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.ros2.rcljava.RCLJava;
import org.ros2.rcljava.time.ClockType;

public class TimeTest {
  @Test
  public final void testSystemTime() {
    builtin_interfaces.msg.Time now = Time.now(ClockType.SYSTEM_TIME);
    long javaNowMillis = System.currentTimeMillis();

    long nowNanos = Time.toNanoseconds(now);
    long javaNowNanos = TimeUnit.NANOSECONDS.convert(javaNowMillis, TimeUnit.MILLISECONDS);

    long tolerance = TimeUnit.NANOSECONDS.convert(1000, TimeUnit.MILLISECONDS);
    assertEquals(javaNowNanos, nowNanos, tolerance);
  }

  @Test
  public final void testSteadyTime() {
    int millisecondsToSleep = 100;
    builtin_interfaces.msg.Time now = Time.now(ClockType.STEADY_TIME);
    long javaNowMillis = System.currentTimeMillis();

    try {
      Thread.sleep(millisecondsToSleep);
    } catch (InterruptedException iex) {
      fail("Failed to sleep for " + millisecondsToSleep + " milliseconds");
    }

    builtin_interfaces.msg.Time later = Time.now(ClockType.STEADY_TIME);

    long javaLaterMillis = System.currentTimeMillis();

    long javaNowNanos = TimeUnit.NANOSECONDS.convert(javaNowMillis, TimeUnit.MILLISECONDS);
    long javaLaterNanos = TimeUnit.NANOSECONDS.convert(javaLaterMillis, TimeUnit.MILLISECONDS);

    long tolerance = TimeUnit.NANOSECONDS.convert(1, TimeUnit.MILLISECONDS);

    long javaDifference = javaLaterNanos - javaNowNanos;
    long difference = Time.difference(later, now);
    assertEquals(javaDifference, difference, tolerance);
  }

  @Test
  public final void testROSTime() {
    builtin_interfaces.msg.Time startTime;
    try {
      startTime = Time.now(ClockType.ROS_TIME);
    } catch (UnsupportedOperationException uoe) {
      return;
    }

    fail("ROS time is not supported yet");
  }
}