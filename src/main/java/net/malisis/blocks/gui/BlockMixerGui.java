package net.malisis.blocks.gui;

import com.google.common.eventbus.Subscribe;

import net.malisis.blocks.MalisisBlocks;
import net.malisis.blocks.MalisisBlocksSettings;
import net.malisis.blocks.tileentity.BlockMixerTileEntity;
import net.malisis.core.client.gui.Anchor;
import net.malisis.core.client.gui.MalisisGui;
import net.malisis.core.client.gui.component.UISlot;
import net.malisis.core.client.gui.component.container.UIContainer;
import net.malisis.core.client.gui.component.container.UIPlayerInventory;
import net.malisis.core.client.gui.component.decoration.UIProgressBar;
import net.malisis.core.client.gui.component.decoration.UITooltip;
import net.malisis.core.client.gui.component.interaction.UICheckBox;
import net.malisis.core.client.gui.render.BackgroundTexture.WindowBackground;
import net.malisis.core.inventory.MalisisInventoryContainer;

public class BlockMixerGui extends MalisisGui
{
	private BlockMixerTileEntity tileEntity;

	private UIProgressBar progressBar;
	private UIProgressBar progressBarReversed;
	private UICheckBox cbRender;

	public BlockMixerGui(BlockMixerTileEntity tileEntity, MalisisInventoryContainer container)
	{
		setInventoryContainer(container);
		this.tileEntity = tileEntity;
	}

	@Override
	public void construct()
	{

		UIContainer<?> window = new UIContainer<>(this, "tile.block_mixer.name", 176, 166);
		window.setBackground(new WindowBackground(this));

		UISlot firstInputSlot = new UISlot(this, tileEntity.firstInput).setPosition(-60, 20, Anchor.CENTER);
		UISlot secondInputSlot = new UISlot(this, tileEntity.secondInput).setPosition(60, 20, Anchor.CENTER);
		UISlot outputSlot = new UISlot(this, tileEntity.output).setPosition(0, 20, Anchor.CENTER);

		progressBar = new UIProgressBar(this).setPosition(-30, 21, Anchor.CENTER);
		progressBarReversed = new UIProgressBar(this).setPosition(30, 21, Anchor.CENTER).setReversed();

		cbRender = new UICheckBox(this, "gui.block_mixer.simple_rendering").setPosition(0, 50, Anchor.CENTER).register(this);
		cbRender.setTooltip(new UITooltip(this, "gui.block_mixer.simple_rendering_tooltip"));

		UIPlayerInventory playerInv = new UIPlayerInventory(this, inventoryContainer.getPlayerInventory());

		window.add(firstInputSlot);
		window.add(secondInputSlot);
		window.add(outputSlot);

		window.add(progressBar);
		window.add(progressBarReversed);

		window.add(cbRender);

		window.add(playerInv);

		addToScreen(window);
	}

	@Override
	public void updateScreen()
	{
		progressBar.setProgress(tileEntity.getMixTimer());
		progressBarReversed.setProgress(tileEntity.getMixTimer());

		cbRender.setChecked(MalisisBlocksSettings.simpleMixedBlockRendering.get());
	}

	@Subscribe
	public void onCheck(UICheckBox.CheckEvent event)
	{
		MalisisBlocksSettings.simpleMixedBlockRendering.set(event.isChecked());
		MalisisBlocks.settings.save();
	}

}