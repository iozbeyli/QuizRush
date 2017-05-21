package com.fromthemind.quizrush;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;

/**
 * Created by Melih on 21.05.2017.
 */

public class ViewControllers {

    public static class LoaderController{
        Context context;
        View rest;
        View loader;
        public LoaderController(final View loader, final View rest, Context context){
            this.loader=loader;
            this.context=context;
            this.rest=rest;
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
        public void showProgress(final boolean show) {
            // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
            // for very easy animations. If available, use these APIs to fade-in
            // the progress spinner.
            Log.d("Loader",""+show);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                int shortAnimTime = context.getResources().getInteger(android.R.integer.config_shortAnimTime);

                rest.setVisibility(show ? View.GONE : View.VISIBLE);
                rest.animate().setDuration(shortAnimTime).alpha(
                        show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        rest.setVisibility(show ? View.GONE : View.VISIBLE);
                    }
                });

                loader.setVisibility(show ? View.VISIBLE : View.GONE);
                loader.animate().setDuration(shortAnimTime).alpha(
                        show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        loader.setVisibility(show ? View.VISIBLE : View.GONE);
                    }
                });
            } else {
                // The ViewPropertyAnimator APIs are not available, so simply show
                // and hide the relevant UI components.
                loader.setVisibility(show ? View.VISIBLE : View.GONE);
                rest.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        }
    }
}
