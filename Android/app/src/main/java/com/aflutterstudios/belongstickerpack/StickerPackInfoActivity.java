/*
 * Copyright (c) WhatsApp Inc. and its affiliates.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.aflutterstudios.belongstickerpack;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class StickerPackInfoActivity extends BaseActivity {

    private static final String TAG = "StickerPackInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_pack_info);

        final String trayIconUriString = getIntent().getStringExtra(StickerPackDetailsActivity.EXTRA_STICKER_PACK_TRAY_ICON);
        final String website = getIntent().getStringExtra(StickerPackDetailsActivity.EXTRA_STICKER_PACK_WEBSITE);
        final String pubTwitter = "https://www.twitter.com/aflutterstudios";
        final String artTwitter = "https://www.twitter.com/asiearts";
        final String devTwitter = "https://www.twitter.com/shinthedev";
        final String privacyPolicy = getIntent().getStringExtra(StickerPackDetailsActivity.EXTRA_STICKER_PACK_PRIVACY_POLICY);

        final TextView trayIcon = findViewById(R.id.tray_icon);
        try {
            final InputStream inputStream = getContentResolver().openInputStream(Uri.parse(trayIconUriString));
            final BitmapDrawable trayDrawable = new BitmapDrawable(getResources(), inputStream);
            final Drawable emailDrawable = getDrawableForAllAPIs(R.drawable.sticker_3rdparty_email);
            trayDrawable.setBounds(new Rect(0, 0, emailDrawable.getIntrinsicWidth(), emailDrawable.getIntrinsicHeight()));
            trayIcon.setCompoundDrawables(trayDrawable, null, null, null);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "could not find the uri for the tray image:" + trayIconUriString);
        }

        final TextView viewWebpage = findViewById(R.id.view_webpage);
        if (TextUtils.isEmpty(website)) {
            viewWebpage.setVisibility(View.GONE);
        } else {
            viewWebpage.setOnClickListener(v -> launchWebpage(website));
        }

        final TextView publisherTwitter = findViewById(R.id.publisher_twitter);
        if (TextUtils.isEmpty(pubTwitter)) {
            publisherTwitter.setVisibility(View.GONE);
        } else {
            publisherTwitter.setOnClickListener(v -> launchWebpage(pubTwitter));
        }

        final TextView artistTwitter = findViewById(R.id.artist_credit);
        if (TextUtils.isEmpty(artTwitter)) {
            artistTwitter.setVisibility(View.GONE);
        } else {
            artistTwitter.setOnClickListener(v -> launchWebpage(artTwitter));
        }

        final TextView developerTwitter = findViewById(R.id.dev_credit);
        if (TextUtils.isEmpty(devTwitter)) {
            developerTwitter.setVisibility(View.GONE);
        } else {
            developerTwitter.setOnClickListener(v -> launchWebpage(devTwitter));
        }

        final TextView viewPrivacyPolicy = findViewById(R.id.privacy_policy);
        if (TextUtils.isEmpty(privacyPolicy)) {
            viewPrivacyPolicy.setVisibility(View.GONE);
        } else {
            viewPrivacyPolicy.setOnClickListener(v -> launchWebpage(privacyPolicy));
        }
    }


    private void launchWebpage(String website) {
        Uri uri = Uri.parse(website);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private Drawable getDrawableForAllAPIs(@DrawableRes int id) {
        if (Build.VERSION.SDK_INT >= 21) {
            return getDrawable(id);
        } else {
            return getResources().getDrawable(id);
        }
    }
}
