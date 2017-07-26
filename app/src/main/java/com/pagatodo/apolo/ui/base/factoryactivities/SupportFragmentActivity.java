package com.pagatodo.apolo.ui.base.factoryactivities;

import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.pagatodo.apolo.R;
import com.pagatodo.apolo.ui.base.factoryfragments.BaseFragment;

/**
 * @author Juan Guerra
 * @version 1.1
 *
 * Updated 16/05/2017 by Alberto Vazquez
 *
 * This class implements multiple methods to handle Fragments
 *
 * Created by jvazquez on 16/05/2017.
 */

public abstract  class SupportFragmentActivity extends AppCompatActivity {
    @IdRes
    protected int idContainerFragments = -1;
    protected FragmentTransaction fragmentTransaction;
    protected FragmentManager fragmentManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.fragmentManager = getSupportFragmentManager();
        idContainerFragments = setIdContainerFragments();
    }
    @IdRes
    protected abstract int setIdContainerFragments();

    public enum TRANSITION {
        FORDWARD(R.anim.slide_from_right, R.anim.slide_to_left),
        BACK(R.anim.slide_from_left, R.anim.slide_to_right),
        FADE(android.R.anim.fade_in, android.R.anim.fade_out),
        NONE(0, 0);

        private int enterAnimation;
        private int exitAnimation;

        private TRANSITION(@AnimRes int enterAnimation, @AnimRes int exitAnimation) {
            this.enterAnimation = enterAnimation;
            this.exitAnimation = exitAnimation;
        }

        public int getEnterAnimation() {
            return enterAnimation;
        }

        public int getExitAnimation() {
            return exitAnimation;
        }
    }

    protected void loadFragment(@NonNull BaseFragment fragment) {
        loadFragment(fragment, idContainerFragments, TRANSITION.NONE, false);
    }

    protected void loadFragment(@NonNull BaseFragment fragment, @IdRes int idContainer) {
        loadFragment(fragment, idContainer, TRANSITION.NONE, false);
    }

    protected void loadFragment(@NonNull BaseFragment fragment, boolean addToBackStack) {
        loadFragment(fragment, idContainerFragments, TRANSITION.NONE, addToBackStack);
    }

    protected void loadFragment(@NonNull BaseFragment fragment, @IdRes int idContainer, boolean addToBackStack) {
        loadFragment(fragment, idContainer, TRANSITION.NONE, addToBackStack);
    }

    protected void loadFragment(@NonNull BaseFragment fragment, @NonNull TRANSITION direction) {
        loadFragment(fragment, idContainerFragments, direction, false);
    }

    protected void loadFragment(@NonNull BaseFragment fragment, @IdRes int idContainer, @NonNull TRANSITION direction) {
        loadFragment(fragment, idContainer, direction, false);
    }

    protected void loadFragment(@NonNull BaseFragment fragment, @NonNull TRANSITION direction,
                                boolean addToBackStack) {
        loadFragment(fragment, idContainerFragments, direction, addToBackStack);
    }

    protected void loadFragment(@NonNull BaseFragment fragment, @IdRes int idContainer, @NonNull TRANSITION direction,
                                boolean addToBackStack) {
        this.idContainerFragments = idContainer;
        fragmentTransaction = fragmentManager.beginTransaction();
        if (!direction.equals(TRANSITION.NONE)) {
            fragmentTransaction.setCustomAnimations(direction.getEnterAnimation(), direction.getExitAnimation());
        }
        fragmentTransaction.replace(idContainer, fragment, fragment.getFragmentTag());
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getFragmentTag());
        }
        fragmentTransaction.commit();
    }
    protected BaseFragment getCurrentFragment() {
        if (idContainerFragments != -1) {
            return getCurrentFragment(idContainerFragments);
        }
        return null;
    }
    protected BaseFragment getCurrentFragment(@IdRes int idContainer) {
        return (BaseFragment) fragmentManager.findFragmentById(idContainer);
    }
    @Override
    public void onBackPressed() {
//        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//            if(getSupportFragmentManager().getBackStackEntryCount() == 1){
//                finish();
//                return;
//            }
//            getSupportFragmentManager().popBackStack();
//        } else {
//            super.onBackPressed();
//        }
        //super.onBackPressed();
        BaseFragment currentFragment = getCurrentFragment();
        if(currentFragment != null && currentFragment.getProgressViewStatusVisibility()){
            return;
        }
    }
}