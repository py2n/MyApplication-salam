/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*- */
/*
 * This file is part of the LibreOffice project.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.example.mohammad.myapplication.connectToServer.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mohammad.myapplication.R;
import com.example.mohammad.myapplication.connectToServer.adapter.ComputersPagerAdapter;
import com.example.mohammad.myapplication.connectToServer.fragment.ComputersFragment;
import com.example.mohammad.myapplication.connectToServer.util.Intents;

public class ComputersActivity extends AppCompatActivity implements ActionBar.TabListener, ViewPager.OnPageChangeListener {
    private static final int REQUEST_ENABLE_BT = 0;
    private static final String SELECT_BLUETOOTH = "SELECT_BLUETOOTH";
    private static final BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
    private static boolean disableBTOnQuit = btAdapter != null && !btAdapter.isEnabled();
    private static Tab btTab;
    private static Tab wifiTab;
    private boolean isInitializing;
    private ComputersPagerAdapter computersPagerAdapter = new ComputersPagerAdapter(getSupportFragmentManager());

    @Override
    protected void onCreate(Bundle aSavedInstanceState) {
        super.onCreate(aSavedInstanceState);
        isInitializing = true;

        setContentView(R.layout.activity_computers);
        // Looks hacky but it seems to be the best way to set activity’s title
        // different to application’s label. The other way is setting title
        // to intents filter but it shows wrong label for recent apps screen then.

        ActionBar aActionBar = getSupportActionBar();

        aActionBar.setTitle(R.string.title_computers);
        aActionBar.setDisplayShowTitleEnabled(true);


        wifiTab = aActionBar.newTab().setTabListener(this)
                .setText(R.string.title_wifi);

        btTab = aActionBar.newTab().setTabListener(this)
                .setText(R.string.title_bluetooth);

        computersPagerAdapter.addFragment(ComputersFragment.Type.WIFI);
        aActionBar.addTab(wifiTab);


        if (btAdapter != null) {
            computersPagerAdapter.addFragment(ComputersFragment.Type.WIFI);
            aActionBar.addTab(wifiTab);
        }

//        computersPagerAdapter.addFragment(ComputersFragment.Type.WIFI);

        ViewPager aComputersPager = (ViewPager) findViewById(R.id.pager_computers);
        aComputersPager.setAdapter(computersPagerAdapter);
        aComputersPager.setOnPageChangeListener(this);

        // select wifitab - onStart() decides whether BT-Tab should be selected
        // when the user starts the remote (and thus trigger the BT-enable
        // intent in case BT was disabled)
        isInitializing = false;
        aActionBar.addTab(wifiTab, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode != RESULT_OK) {
                getSupportActionBar().selectTab(wifiTab);
            }
        }
    }

    @Override
    public void onTabSelected(Tab aTab, FragmentTransaction aTransaction) {
        ((ViewPager) findViewById(R.id.pager_computers)).setCurrentItem(aTab
                .getPosition());
        supportInvalidateOptionsMenu();
        if (isInitializing) { return; }
        if (aTab.equals(btTab) && !btAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab aTab, FragmentTransaction aTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab aTab, FragmentTransaction aTransaction) {
    }

    @Override
    public void onPageSelected(int aPosition) {
        getSupportActionBar().setSelectedNavigationItem(aPosition);
    }

    @Override
    public void onPageScrolled(int aPosition, float aPositionOffset, int aPositionOffsetPixels) {
    }

    @Override
    public void onPageScrollStateChanged(int aPosition) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu aMenu) {
        getMenuInflater().inflate(R.menu.menu_action_bar_computers, aMenu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu aMenu) {
        aMenu.findItem(R.id.menu_add_computer)
            .setVisible(wifiTab.equals(getSupportActionBar().getSelectedTab()));

        MenuItem btDiscovery = aMenu.findItem(R.id.menu_start_discovery);
        if( btAdapter != null && btAdapter.isDiscovering()) {
            btDiscovery.setEnabled(false);
            MenuItemCompat.setActionView(btDiscovery, R.layout.progress);
        }
        btDiscovery.setVisible(btTab.equals(getSupportActionBar().getSelectedTab()));

        return super.onPrepareOptionsMenu(aMenu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem aMenuItem) {
        switch (aMenuItem.getItemId()) {
            case R.id.menu_settings:
                callSettingsActivity();
                return true;

            case R.id.menu_requirements:
                callRequirementsActivity();
                return true;

            default:
                return super.onOptionsItemSelected(aMenuItem);
        }
    }

    private void callSettingsActivity() {
        Intent aIntent = Intents.buildSettingsIntent(this);
        startActivity(aIntent);
    }

    private void callRequirementsActivity() {
        Intent aIntent = Intents.buildRequirementsIntent(this);
        startActivity(aIntent);
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(SELECT_BLUETOOTH, btTab.equals(getSupportActionBar()
                .getSelectedTab()));
        editor.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        if (sharedPref.getBoolean(SELECT_BLUETOOTH, btAdapter != null)) {
            getSupportActionBar().selectTab(btTab);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing() && disableBTOnQuit) {
            btAdapter.disable();
        }
    }
}

/* vim:set shiftwidth=4 softtabstop=4 expandtab: */
