/* -*- Mode: Java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*- */
/*
 * This file is part of the LibreOffice project.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.example.mohammad.myapplication.connectToServer.communication;

interface MessagesListener {
    void onPinValidation();

    void onSuccessfulPairing();

    void onSlideShowStart(int aSlidesCount, int aCurrentSlideIndex);

    void onSlideShowFinish();

    void onSlideChanged(int aCurrentSlideIndex);

    void onSlidePreview(int aSlideIndex, byte[] aPreview);

    void onSlideNotes(int aSlideIndex, String aNotes);
}

/* vim:set shiftwidth=4 softtabstop=4 expandtab: */
