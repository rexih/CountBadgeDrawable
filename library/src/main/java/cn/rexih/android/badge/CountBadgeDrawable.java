package cn.rexih.android.badge;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * @author huangwr
 * @version %I%, %G%
 * @package com.githang.badge
 * @file CountBadgeDrawable
 * @date 2019/1/21
 */
public class CountBadgeDrawable extends Drawable {

    private              boolean      showBadge;
    private final        Drawable     origin;
    private final        Drawable     badge;
    private final        float        ratio;
    private static final String       DEFAULT_STUB = "   ";
    private              TextDrawable textDrawable;

    private float xRatioOffset = 0;
    private float yRatioOffset = 0;

    public void setRatioOffset(float xRatioOffset, float yRatioOffset) {
        this.xRatioOffset = xRatioOffset;
        this.yRatioOffset = yRatioOffset;
        this.invalidateSelf();
    }

    public void attachToImageView(ImageView iv) {
        attachToImageView(iv, false);
    }

    public void attachToImageView(ImageView iv, boolean clipSelf) {
        iv.setImageDrawable(this);
        clipSelf(iv, clipSelf);
    }

    public void attachToBg(View view, boolean clipSelf) {
        view.setBackgroundDrawable(this);
        clipSelf(view, clipSelf);

    }

    public void attachToBg(View view) {
        attachToBg(view, true);
    }

    public static void clipSelf(View view, boolean clipSelf) {
        if (view != null && //
                view.getParent() != null) {
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            viewGroup.setClipChildren(clipSelf);
        }
    }

    public static void clipSelf(View view) {
        clipSelf(view, true);
    }

    public CountBadgeDrawable(Context context, Drawable origin, Drawable badge, float ratio) {
        super();
        this.origin = origin;
        this.badge = badge;
        this.ratio = ratio;
        this.badge.setBounds(0, 0, this.getBadgeWidth(), this.getBadgeHeight());
        textDrawable = genTextDrawable(context, DEFAULT_STUB);
    }

    public final void unbindBadge() {
        this.showBadge = false;
        this.invalidateSelf();
    }

    public void setAlert(String alert) {
        if (alert == null) {
            return;
        }
        showBadge = true;
        textDrawable.setText(alert);
        textDrawable.setTextSize(scaleTextByRatio(alert));
        this.invalidateSelf();
    }

    private final int getBadgeHeight() {
        return (int) ((float) this.origin.getIntrinsicHeight() * this.ratio);
    }

    private final int getXOffset() {
        return (int) ((float) this.origin.getIntrinsicWidth() * this.xRatioOffset);
    }

    private final int getYOffset() {
        return (int) ((float) this.origin.getIntrinsicHeight() * this.yRatioOffset);
    }

    private final int getBadgeWidth() {
        return (int) ((float) this.origin.getIntrinsicWidth() * this.ratio);
    }

    @Override
    public void draw(Canvas canvas) {
        this.origin.draw(canvas);
        if (this.showBadge) {
            int bw = this.getBadgeWidth();
            int bh = this.getBadgeHeight();
            int tw = textDrawable.getIntrinsicWidth();
            int th = textDrawable.getIntrinsicHeight();
            float radiusX = (float) bw / 2.0F;
            float radiusY = (float) bh / 2.0F;
            float x = (float) this.getBounds().right - radiusX - getXOffset();
            float y = (float) this.getBounds().top - radiusY + getYOffset();
            canvas.save();
            canvas.translate(x, y);
            this.badge.draw(canvas);
            canvas.restore();
            canvas.save();
            canvas.translate(x + (bw - tw) / 2, y + (bh - th) / 2);
            textDrawable.draw(canvas);
            canvas.restore();
        }

    }


    public int[] getState() {
        return origin.getState();
    }

    public boolean setState(int[] stateSet) {
        return this.origin.setState(stateSet);
    }


    public Drawable.ConstantState getConstantState() {
        return origin.getConstantState();
    }

    public boolean isStateful() {
        return origin.isStateful();
    }

    public void jumpToCurrentState() {
        origin.jumpToCurrentState();
    }

    @Override
    public void setAlpha(int alpha) {
        origin.setAlpha(alpha);
    }

    @Override
    public int getOpacity() {
        return origin.getOpacity();
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        origin.setColorFilter(colorFilter);
    }

    public int getIntrinsicHeight() {
        return origin.getIntrinsicHeight();
    }

    public int getIntrinsicWidth() {
        return origin.getIntrinsicWidth();
    }

    public void setBounds(Rect bounds) {
        super.setBounds(bounds);
        origin.setBounds(bounds);
    }

    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        origin.setBounds(left, top, right, bottom);
    }

    @TargetApi(21)
    public void setTint(int tintColor) {
        super.setTint(tintColor);
        this.origin.setTint(tintColor);
    }

    @TargetApi(21)
    public void setTintList(ColorStateList tint) {
        super.setTintList(tint);
        this.origin.setTintList(tint);
    }

    @TargetApi(21)
    public void setTintMode(PorterDuff.Mode tintMode) {
        super.setTintMode(tintMode);
        this.origin.setTintMode(tintMode);
    }

    private TextDrawable genTextDrawable(Context context, String text) {
        TextDrawable textDrawable = new TextDrawable(context);
        textDrawable.setText(text);
        textDrawable.setTextColor(Color.WHITE);
        textDrawable.setTextSize(scaleTextByRatio(text));
        textDrawable.setTextAlign(Layout.Alignment.ALIGN_CENTER);
        return textDrawable;
    }

    private float scaleTextByRatio(String text) {
        int protoWidth = badge.getIntrinsicWidth();
        int badgeWidth = getBadgeWidth();
        float ratio = badgeWidth * 1f / protoWidth;
        if (text.length() > 2) {
            return 7 * ratio;
        } else {
            return 9 * ratio;
        }
    }
}
