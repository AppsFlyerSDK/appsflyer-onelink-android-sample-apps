package com.kuku.muku.kukumukubasicapp;

import android.os.Bundle;

import com.kuku.muku.kukumukubasicapp.R;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.util.BRANCH_STANDARD_EVENT;
import io.branch.referral.util.BranchContentSchema;
import io.branch.referral.util.BranchEvent;
import io.branch.referral.util.ContentMetadata;
import io.branch.referral.util.CurrencyType;
import io.branch.referral.util.ProductCategory;

public class ApplesActivity extends FruitActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStaticAttributes("apples");

        // Create a BranchUniversalObject with your content data
        BranchUniversalObject buo = new BranchUniversalObject()
                .setCanonicalIdentifier("myprod/1234")
                .setCanonicalUrl("https://test_canonical_url")
                .setTitle("test_title")
                .setContentMetadata(
                        new ContentMetadata()
                                .addCustomMetadata("custom_metadata_key1", "custom_metadata_val1")
                                .addCustomMetadata("custom_metadata_key1", "custom_metadata_val1")
                                .addImageCaptions("image_caption_1", "image_caption2", "image_caption3")
                                .setAddress("Street_Name", "test city", "test_state", "test_country", "test_postal_code")
                                .setLocation(-151.67, -124.0)
                                .setPrice(10.0, CurrencyType.USD)
                                .setProductBrand("test_prod_brand")
                                .setProductCategory(ProductCategory.APPAREL_AND_ACCESSORIES)
                                .setProductName("test_prod_name")
                                .setProductCondition(ContentMetadata.CONDITION.EXCELLENT)
                                .setProductVariant("test_prod_variant")
                                .setQuantity(1.5)
                                .setSku("test_sku")
                                .setContentSchema(BranchContentSchema.COMMERCE_PRODUCT))
                .addKeyWord("keyword1")
                .addKeyWord("keyword2");

//  Create an event
        new BranchEvent(BRANCH_STANDARD_EVENT.ADD_TO_CART)
                .setAffiliation("test_affiliation")
                .setCustomerEventAlias("my_custom_alias")
                .setCoupon("Coupon Code")
                .setCurrency(CurrencyType.USD)
                .setDescription("Customer added item to cart")
                .setShipping(0.0)
                .setTax(9.75)
                .setRevenue(1.5)
                .setSearchQuery("Test Search query")
                .addCustomDataProperty("Custom_Event_Property_Key1", "Custom_Event_Property_val1")
                .addCustomDataProperty("Custom_Event_Property_Key2", "Custom_Event_Property_val2")
                .addContentItems(buo) // Add a BranchUniversalObject to the event (cannot be empty)
                .logEvent(this); // Log the event
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_apples;
    }
}