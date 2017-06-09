package com.mangu.personalcityhelper.inyection.components;

import com.mangu.personalcityhelper.inyection.PerFragment;
import com.mangu.personalcityhelper.inyection.modules.FragmentModule;

import dagger.Subcomponent;

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

}