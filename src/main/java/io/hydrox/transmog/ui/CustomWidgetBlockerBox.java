/*
 * Copyright (c) 2020, Hydrox6 <ikada@protonmail.ch>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package io.hydrox.transmog.ui;

import net.runelite.api.FontID;
import net.runelite.api.widgets.JavaScriptCallback;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetTextAlignment;
import net.runelite.api.widgets.WidgetType;
import java.awt.Color;

public class CustomWidgetBlockerBox extends CustomWidget
{
	private static final int TOP_LEFT_CORNER_SIZE = 31;
	private static final int BOTTOM_RIGHT_CORNER_SIZE = 51;

	private Widget leftBar;
	private Widget middleBar;
	private Widget rightBar;
	private Widget cornerBlocker;
	private Widget textStage1;
	private Widget textStage2;
	private Widget arrow;

	public CustomWidgetBlockerBox(Widget parent, String name)
	{
		super(parent, name);
	}

	public void setHidden(boolean state)
	{
		leftBar.setHidden(state);
		middleBar.setHidden(state);
		rightBar.setHidden(state);
		cornerBlocker.setHidden(state);
		textStage1.setHidden(state);
		textStage2.setHidden(state);
		arrow.setHidden(state);
	}

	public void setTutorialState(int state)
	{
		switch (state)
		{
			case 1:
				textStage1.setHidden(false);
				textStage2.setHidden(true);
				arrow.setHidden(true);
				cornerBlocker.setHidden(false);
				return;
			case 2:
				textStage1.setHidden(true);
				textStage2.setHidden(false);
				arrow.setHidden(false);
				cornerBlocker.setHidden(true);
		}
	}

	@Override
	public void layout(int x, int y)
	{
		super.layout(x, y);

		layoutWidget(leftBar, x, y + TOP_LEFT_CORNER_SIZE);
		layoutWidget(middleBar, x + TOP_LEFT_CORNER_SIZE, y);
		layoutWidget(rightBar, x + width - BOTTOM_RIGHT_CORNER_SIZE, y);
		layoutWidget(cornerBlocker, x + width - BOTTOM_RIGHT_CORNER_SIZE, y + height - BOTTOM_RIGHT_CORNER_SIZE);

		layoutWidget(textStage1, x + 10, y);
		layoutWidget(textStage2, x + 10, y);
		layoutWidget(arrow, x + width - 100, y + height - 100);

		parent.revalidate();
	}

	@Override
	public void create()
	{
		JavaScriptCallback empty = ev -> {};

		leftBar = createRectangleWidget(TOP_LEFT_CORNER_SIZE, height - TOP_LEFT_CORNER_SIZE);
		leftBar.setFilled(true);
		leftBar.setOpacity(120);
		leftBar.setHasListener(true);
		leftBar.setNoClickThrough(true);
		leftBar.setOnOpListener(empty);

		middleBar = createRectangleWidget(width - (TOP_LEFT_CORNER_SIZE + BOTTOM_RIGHT_CORNER_SIZE), height);
		middleBar.setFilled(true);
		middleBar.setOpacity(120);
		middleBar.setHasListener(true);
		middleBar.setNoClickThrough(true);
		middleBar.setOnOpListener(empty);

		rightBar = createRectangleWidget(BOTTOM_RIGHT_CORNER_SIZE, height - BOTTOM_RIGHT_CORNER_SIZE);
		rightBar.setFilled(true);
		rightBar.setOpacity(120);
		rightBar.setHasListener(true);
		rightBar.setNoClickThrough(true);
		rightBar.setOnOpListener(empty);

		cornerBlocker = createRectangleWidget(BOTTOM_RIGHT_CORNER_SIZE, BOTTOM_RIGHT_CORNER_SIZE);
		cornerBlocker.setFilled(true);
		cornerBlocker.setOpacity(120);
		cornerBlocker.setHasListener(true);
		cornerBlocker.setNoClickThrough(true);
		cornerBlocker.setOnOpListener(empty);

		textStage1 = createTextWidget("The plugin needs to know your base outfit. First, remove all of your equipment.");
		textStage1.setXTextAlignment(WidgetTextAlignment.CENTER);
		textStage1.setYTextAlignment(WidgetTextAlignment.CENTER);
		textStage1.setOriginalWidth(width - 20);
		textStage1.setOriginalHeight(height);
		textStage1.setTextColor(fromRGB(Color.YELLOW));
		textStage1.setFontId(FontID.PLAIN_12);

		textStage2 = createTextWidget("Now click the button in the corner to provide a default state.");
		textStage2.setXTextAlignment(WidgetTextAlignment.CENTER);
		textStage2.setYTextAlignment(WidgetTextAlignment.CENTER);
		textStage2.setOriginalWidth(width - 20);
		textStage2.setOriginalHeight(height);
		textStage2.setTextColor(fromRGB(Color.YELLOW));
		textStage2.setFontId(FontID.PLAIN_12);

		// RL does not have the stuff required to rotate sprites exported (if there is such a thing),
		// so a custom needs be used instead
		arrow = createSpriteWidget(48, 48);
		arrow.setSpriteId(CustomSprites.TUTORIAL_ARROW.getSpriteId());
	}

	private Widget createRectangleWidget(int width, int height)
	{
		final Widget w = parent.createChild(-1, WidgetType.RECTANGLE);
		w.setOriginalHeight(height);
		w.setOriginalWidth(width);
		return w;
	}
}
