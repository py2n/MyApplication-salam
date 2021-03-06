/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*- */
/*
 * This file is part of the LibreOffice project.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.example.mohammad.myapplication.connectToServer.util;

import android.content.Context;
import android.content.Intent;

import com.example.mohammad.myapplication.connectToServer.activity.ComputerConnectionActivity;
import com.example.mohammad.myapplication.connectToServer.activity.ComputerCreationActivity;
import com.example.mohammad.myapplication.connectToServer.activity.RequirementsActivity;
import com.example.mohammad.myapplication.connectToServer.activity.SettingsActivity;
import com.example.mohammad.myapplication.connectToServer.activity.SlideShowActivity;
import com.example.mohammad.myapplication.connectToServer.communication.CommunicationService;
import com.example.mohammad.myapplication.connectToServer.communication.Server;

public final class Intents {
    private Intents() {
    }

    public static final class Actions {
        private Actions() {
        }

        public static final String SERVERS_LIST_CHANGED = "SERVERS_LIST_CHANGED";
        public static final String BT_DISCOVERY_CHANGED = "BT_DISCOVERY_CHANGED";

        public static final String PAIRING_SUCCESSFUL = "PAIRING_SUCCESSFUL";
        public static final String PAIRING_VALIDATION = "PAIRING_VALIDATION";

        public static final String CONNECTION_FAILED = "CONNECTION_FAILED";

        public static final String SLIDE_SHOW_RUNNING = "SLIDE_SHOW_RUNNING";
        public static final String SLIDE_SHOW_STOPPED = "SLIDE_SHOW_STOPPED";

        public static final String SLIDE_SHOW_MODE_CHANGED = "SLIDE_SHOW_MODE_CHANGED";

        public static final String SLIDE_CHANGED = "SLIDE_CHANGED";
        public static final String SLIDE_PREVIEW = "SLIDE_PREVIEW";
        public static final String SLIDE_NOTES = "SLIDE_NOTES";

        public static final String TIMER_UPDATED = "TIMER_UPDATED";
        public static final String TIMER_STARTED = "TIMER_STARTED";
        public static final String TIMER_RESUMED = "TIMER_RESUMED";
        public static final String TIMER_CHANGED = "TIMER_CHANGED";

        public static final String GOOGLE_API_CONNECTED="GOOGLE_API_CONNECTED";
        public static final String WEAR_NEXT= "WEAR_NEXT";
        public static final String WEAR_PREVIOUS= "WEAR_PREVIOUS";
/*        public static final String WEAR_PAUSE= "WEAR_PAUSE";
        public static final String WEAR_RESUME= "WEAR_RESUME";*/
        public static final String WEAR_CONNECT= "WEAR_CONNECT";
        public static final String WEAR_EXIT= "WEAR_EXIT";
        public static final String WEAR_PAUSE_RESUME="WEAR_PAUSE_RESUME";

    }

    public static final class Extras {
        private Extras() {
        }

        public static final String MINUTES = "MINUTES";
        public static final String MODE = "MODE";
        public static final String PIN = "PIN";
        public static final String SERVER = "SERVER";
        public static final String SERVER_ADDRESS = "SERVER_ADDRESS";
        public static final String SERVER_NAME = "SERVER_NAME";
        public static final String SLIDE_INDEX = "SLIDE_INDEX";
    }

    public static final class RequestCodes {
        private RequestCodes() {
        }

        public static final int CREATE_SERVER = 1;
    }

    public static Intent buildServersListChangedIntent() {
        return new Intent(Actions.SERVERS_LIST_CHANGED);
    }

    public static Intent buildPairingSuccessfulIntent() {
        return new Intent(Actions.PAIRING_SUCCESSFUL);
    }

    public static Intent buildPairingValidationIntent(String aPin) {
        Intent aIntent = new Intent(Actions.PAIRING_VALIDATION);
        aIntent.putExtra(Extras.PIN, aPin);

        return aIntent;
    }

    public static Intent buildConnectionFailedIntent() {
        return new Intent(Actions.CONNECTION_FAILED);
    }

    public static Intent buildSlideShowRunningIntent() {
        return new Intent(Actions.SLIDE_SHOW_RUNNING);
    }

    public static Intent buildSlideShowStoppedIntent() {
        return new Intent(Actions.SLIDE_SHOW_STOPPED);
    }

    public static Intent buildSlideShowModeChangedIntent(SlideShowActivity.Mode aMode) {
        Intent aIntent = new Intent(Actions.SLIDE_SHOW_MODE_CHANGED);
        aIntent.putExtra(Extras.MODE, aMode);

        return aIntent;
    }

    public static Intent buildSlideChangedIntent(int aSlideIndex) {
        Intent aIntent = new Intent(Actions.SLIDE_CHANGED);
        aIntent.putExtra(Extras.SLIDE_INDEX, aSlideIndex);

        return aIntent;
    }
    public static Intent buildGoogleApiConnectedIntent(){
        return new Intent(Actions.GOOGLE_API_CONNECTED);
    }
    public static Intent buildWearNextIntent() {
        return new Intent(Actions.WEAR_NEXT);
    }
    public static Intent buildWearPreviousIntent() {
        return new Intent(Actions.WEAR_PREVIOUS);
    }
/*    public static Intent buildWearPauseIntent() {
        return new Intent(Actions.WEAR_PAUSE);
    }
    public static Intent buildWearResumeIntent() {
        return new Intent(Actions.WEAR_RESUME);
    }*/
    public static Intent buildWearConnectIntent() {
        return new Intent(Actions.WEAR_CONNECT);
    }
    public static Intent buildWearExitIntent() {
        return new Intent(Actions.WEAR_EXIT);
    }
    public static Intent buildWearPauseResumeIntent() {
        return new Intent(Actions.WEAR_PAUSE_RESUME);
    }

    public static Intent buildSlidePreviewIntent(int aSlideIndex) {
        Intent aIntent = new Intent(Actions.SLIDE_PREVIEW);
        aIntent.putExtra(Extras.SLIDE_INDEX, aSlideIndex);

        return aIntent;
    }

    public static Intent buildSlideNotesIntent(int aSlideIndex) {
        Intent aIntent = new Intent(Actions.SLIDE_NOTES);
        aIntent.putExtra(Extras.SLIDE_INDEX, aSlideIndex);

        return aIntent;
    }

    public static Intent buildComputerConnectionIntent(Context aContext, Server aServer) {
        Intent aIntent = new Intent(aContext, ComputerConnectionActivity.class);
        aIntent.putExtra(Extras.SERVER, aServer);

        return aIntent;
    }

    public static Intent buildComputerCreationIntent(Context aContext) {
        return new Intent(aContext, ComputerCreationActivity.class);
    }

    public static Intent buildComputerCreationResultIntent(String aAddress, String aName) {
        Intent aIntent = new Intent();
        aIntent.putExtra(Extras.SERVER_ADDRESS, aAddress);
        aIntent.putExtra(Extras.SERVER_NAME, aName);

        return aIntent;
    }

    public static Intent buildSlideShowIntent(Context aContext) {
        return new Intent(aContext, SlideShowActivity.class);
    }

    public static Intent buildSettingsIntent(Context aContext) {
        return new Intent(aContext, SettingsActivity.class);
    }

    public static Intent buildRequirementsIntent(Context aContext) {
        return new Intent(aContext, RequirementsActivity.class);
    }

    public static Intent buildCommunicationServiceIntent(Context aContext) {
        return new Intent(aContext, CommunicationService.class);
    }

    public static Intent buildTimerUpdatedIntent() {
        return new Intent(Actions.TIMER_UPDATED);
    }

    public static Intent buildTimerStartedIntent(int aMinutesLength) {
        Intent aIntent = new Intent(Actions.TIMER_STARTED);
        aIntent.putExtra(Extras.MINUTES, aMinutesLength);

        return aIntent;
    }

    public static Intent buildTimerResumedIntent() {
        return new Intent(Actions.TIMER_RESUMED);
    }

    public static Intent buildTimerChangedIntent(int aMinutesLength) {
        Intent aIntent = new Intent(Actions.TIMER_CHANGED);
        aIntent.putExtra(Extras.MINUTES, aMinutesLength);

        return aIntent;
    }
}

/* vim:set shiftwidth=4 softtabstop=4 expandtab: */
