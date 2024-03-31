package com.freezzah.minecities.client.gui.screen;

import com.freezzah.minecities.city.City;
import com.freezzah.minecities.client.gui.menu.CityOverviewMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

import static com.freezzah.minecities.Constants.MOD_ID;

public class CityOverviewScreen extends AbstractContainerScreen<CityOverviewMenu> {
    private static final ResourceLocation BACKGROUND_LOCATION = new ResourceLocation(MOD_ID, "textures/gui/screen/base_background.png");
    private final CityOverviewMenu menu;
    private final City city;

    public CityOverviewScreen(CityOverviewMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.menu = menu;
        this.city = menu.getCity();
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        final int verticalSpacing = 20;
        int offsetTop = this.topPos + 30;
        int offsetLeft = this.leftPos + 20;
        int startOffsetLeftMiddle = this.imageWidth / 2 + offsetLeft;
        graphics.drawString(this.font, "City: ", offsetLeft, offsetTop, 4210752);
        if (city.getCityName() == null) {
            return;
        }
        graphics.drawString(this.font, this.city.getCityName(), startOffsetLeftMiddle, offsetTop, 4210752);
        offsetTop += verticalSpacing;
        graphics.drawString(this.font, "Gold: ", offsetLeft, offsetTop, 4210752);
        graphics.drawString(this.font, Component.literal(String.valueOf(this.menu.getMoney())), startOffsetLeftMiddle, offsetTop, 4210752);
        offsetTop += verticalSpacing;
        graphics.drawString(this.font, "Waste: ", offsetLeft, offsetTop, 4210752);
        graphics.drawString(this.font, Component.literal(String.valueOf(this.menu.getWaste())), startOffsetLeftMiddle, offsetTop, 4210752);
        offsetTop += verticalSpacing;
        graphics.drawString(this.font, "Water: ", offsetLeft, offsetTop, 4210752);
        graphics.drawString(this.font, Component.literal(String.valueOf(this.menu.getWater())), startOffsetLeftMiddle, offsetTop, 4210752);
        offsetTop += verticalSpacing;
        graphics.drawString(this.font, "Food: ", offsetLeft, offsetTop, 4210752);
        graphics.drawString(this.font, Component.literal(String.valueOf(this.menu.getFood())), startOffsetLeftMiddle, offsetTop, 4210752);
        super.render(graphics, mouseX, mouseY, partialTick);
        offsetTop = this.topPos + 30;
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouesY) {
        RenderSystem.setShaderTexture(0, BACKGROUND_LOCATION);
        graphics.blit(BACKGROUND_LOCATION, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        //Ignored, do not render;
    }
}
