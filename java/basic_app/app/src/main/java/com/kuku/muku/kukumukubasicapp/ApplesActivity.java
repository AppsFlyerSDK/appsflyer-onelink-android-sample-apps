package com.kuku.muku.kukumukubasicapp;

import android.os.Bundle;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.util.BRANCH_STANDARD_EVENT;
import io.branch.referral.util.BranchContentSchema;
import io.branch.referral.util.BranchEvent;
import io.branch.referral.util.ContentMetadata;
import io.branch.referral.util.CurrencyType;

public class ApplesActivity extends FruitActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStaticAttributes("apples");

        // Create a BranchUniversalObject with your content data
        BranchUniversalObject buo = new BranchUniversalObject()
                .setCanonicalIdentifier("myprod/1234")
                .setCanonicalUrl("https://test_canonical_url")
                .setContentMetadata(
                        new ContentMetadata()
                                .setAddress("Street_Name", "test city", "test_state", "test_country", "test_postal_code")
                                .setLocation(-151.67, -124.0)
                                .setPrice(10.0, CurrencyType.USD)
                                .setQuantity(1.5)
                                .setSku("test_sku")
                                .setContentSchema(BranchContentSchema.COMMERCE_PRODUCT));

//  Create an event
        new BranchEvent(BRANCH_STANDARD_EVENT.ADD_TO_CART)
                .setCoupon("Coupon Code")
                .setCurrency(CurrencyType.USD)
                .setDescription("Customer added item to cart")
                .setRevenue(1.5)
                .setSearchQuery("Test Search query")
                .addContentItems(buo) // Add a BranchUniversalObject to the event (cannot be empty)
                .logEvent(this); // Log the event
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_apples;
    }
}