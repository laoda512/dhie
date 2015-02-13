/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package util;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Interpolator;

/**
 * An interpolator where the change bounces at the end.
 */
public class BreathInterpolator implements Interpolator {
    public BreathInterpolator() {
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public BreathInterpolator(float k) {
    	this.k=k*2;
    }
    float k;
    private static float bounce(float t) {
        return t * t * 8.0f;
    }

    public float getInterpolation(float t) {
		return (float) (-Math.pow(t*2-1, k)+1);
    }
}