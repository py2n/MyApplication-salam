/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*- */
/*
 * This file is part of the LibreOffice project.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.example.mohammad.myapplication.connectToServer.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.example.mohammad.myapplication.connectToServer.fragment.ComputerConnectionFragment;
import com.example.mohammad.myapplication.connectToServer.util.*;
import com.example.mohammad.myapplication.connectToServer.communication.Server;

public class ComputerConnectionActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle aSavedInstanceState) {
        super.onCreate(aSavedInstanceState);

        setUpHomeButton();
        setUpTitle();
        setUpFragment();
    }

    private void setUpHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void setUpTitle() {
        String aComputerName = getComputer().getName();

        getSupportActionBar().setSubtitle(aComputerName);
    }

    private Server getComputer() {
        return getIntent().getParcelableExtra(Intents.Extras.SERVER);
    }

    private void setUpFragment() {
        Server aComputer = getComputer();
        Fragment aFragment = ComputerConnectionFragment.newInstance(aComputer);

        Fragments.Operator.add(this, aFragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem aMenuItem) {
        switch (aMenuItem.getItemId()) {
            case android.R.id.home:
                navigateUp();
                return true;

            default:
                return super.onOptionsItemSelected(aMenuItem);
        }
    }

    private void navigateUp() {
        finish();
    }
}

/* vim:set shiftwidth=4 softtabstop=4 expandtab: */
