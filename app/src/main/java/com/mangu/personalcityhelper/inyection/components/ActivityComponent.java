package com.mangu.personalcityhelper.inyection.components;

import com.mangu.personalcityhelper.inyection.PerActivity;
import com.mangu.personalcityhelper.inyection.modules.ActivityModule;
import com.mangu.personalcityhelper.ui.base.BaseActivity;
import com.mangu.personalcityhelper.ui.beach.BeachActivity;
import com.mangu.personalcityhelper.ui.events.EventsActivity;
import com.mangu.personalcityhelper.ui.main.MainActivity;
import com.mangu.personalcityhelper.ui.news.NewsActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(BaseActivity baseActivity);

    void inject(MainActivity mainActivity);

    void inject(NewsActivity newsActivity);

    void inject(EventsActivity eventsActivity);

    void inject(BeachActivity beachActivity);
}

