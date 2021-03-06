/*
 * Copyright (C) 2016 The Android Open Source Project
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

package android.support.v17.leanback.app;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.VerticalGridPresenter;

import org.junit.Test;
import org.junit.runner.RunWith;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class VerticalGridFragmentTest {

    public static class GridFragment extends VerticalGridFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (savedInstanceState == null) {
                prepareEntranceTransition();
            }
            VerticalGridPresenter gridPresenter = new VerticalGridPresenter();
            gridPresenter.setNumberOfColumns(3);
            setGridPresenter(gridPresenter);
            setAdapter(new ArrayObjectAdapter());
        }
    }

    public static class ImmediateRemoveFragmentActivity extends Activity {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            new Handler().postDelayed(new Runnable(){
                public void run() {
                    GridFragment f = new GridFragment();
                    ImmediateRemoveFragmentActivity.this.getFragmentManager().beginTransaction()
                            .replace(android.R.id.content, f, null).commit();
                    f.startEntranceTransition();
                    ImmediateRemoveFragmentActivity.this.getFragmentManager().beginTransaction()
                            .replace(android.R.id.content, new Fragment(), null).commit();
                }
            }, 500);
        }
    }

    @Test
    public void immediateRemoveFragment() throws Throwable {
        Intent intent = new Intent();
        ActivityTestRule<ImmediateRemoveFragmentActivity> activityTestRule =
                new ActivityTestRule<>(ImmediateRemoveFragmentActivity.class, false, false);
        ImmediateRemoveFragmentActivity activity = activityTestRule.launchActivity(intent);

        Thread.sleep(1000);
    }

}
