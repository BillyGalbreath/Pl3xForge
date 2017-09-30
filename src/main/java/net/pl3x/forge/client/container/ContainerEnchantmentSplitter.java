package net.pl3x.forge.client.container;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.pl3x.forge.client.block.ModBlocks;
import net.pl3x.forge.client.inventory.EnchantmentSplitterInventoryInputs;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class ContainerEnchantmentSplitter extends Container {
    private final IInventory resultSlotTool;
    private final IInventory resultSlotBook;
    private final EnchantmentSplitterInventoryInputs inputSlots;
    private final World world;
    public final BlockPos selfPosition;
    public boolean BAD_INPUT_TOOL = false;
    public boolean BAD_INPUT_BOOK = false;
    public boolean NEW_INPUT_TOOL = false;

    public ContainerEnchantmentSplitter(InventoryPlayer playerInventory, final World worldIn, final BlockPos blockPosIn) {
        this.selfPosition = blockPosIn;
        this.world = worldIn;

        resultSlotTool = new InventoryCraftResult();
        resultSlotBook = new InventoryCraftResult();
        inputSlots = new EnchantmentSplitterInventoryInputs("splitter", true, 2) {
            public void markDirty() {
                super.markDirty();
                onCraftMatrixChanged(this);
            }
        };

        // splitter slots
        addSlotToContainer(new Slot(inputSlots, 0, 80, 22) {
            public boolean isItemValid(ItemStack stack) {
                return stack.getItem() != Items.BOOK && (stack.isItemEnchanted() || stack.isItemEnchantable() || stack.getItem() == Items.ENCHANTED_BOOK);
            }
        });
        addSlotToContainer(new Slot(inputSlots, 1, 80, 47) {
            public boolean isItemValid(ItemStack stack) {
                return stack.getItem() == Items.BOOK;
            }
        });
        addSlotToContainer(new Slot(resultSlotTool, 2, 134, 22) {
            public boolean isItemValid(ItemStack stack) {
                return false;
            }

            public boolean canTakeStack(EntityPlayer player) {
                return getHasStack() && (player.capabilities.isCreativeMode || player.experienceLevel >= 3);
            }

            public ItemStack onTake(EntityPlayer player, ItemStack stack) {
                ItemStack resultBook = resultSlotBook.getStackInSlot(1).copy();
                if (!resultBook.isEmpty()) {
                    if (!player.capabilities.isCreativeMode) {
                        player.addExperienceLevel(-3);
                    }

                    // put the result book in the player inventory too
                    mergeItemStack(resultBook, 4, 40, true);

                    // set result book to air
                    resultSlotBook.setInventorySlotContents(1, ItemStack.EMPTY);

                    // clear the input tool
                    inputSlots.setInventorySlotContents(false, 0, ItemStack.EMPTY);

                    // decrease the amount of blank books
                    ItemStack books = inputSlots.getStackInSlot(1).copy();
                    if (books.getCount() < 2) {
                        inputSlots.setInventorySlotContents(false, 1, ItemStack.EMPTY);
                    } else {
                        books.setCount(books.getCount() - 1);
                        inputSlots.setInventorySlotContents(false, 1, books);
                    }
                    new Thread(() -> {
                        try {
                            TimeUnit.MILLISECONDS.sleep(50);
                        } catch (InterruptedException ignore) {
                        }
                        onCraftMatrixChanged(inputSlots);
                    }).start();
                }
                return stack;
            }
        });
        addSlotToContainer(new Slot(resultSlotBook, 3, 134, 47) {
            public boolean isItemValid(ItemStack stack) {
                return false;
            }

            public boolean canTakeStack(EntityPlayer player) {
                return getHasStack() && (player.capabilities.isCreativeMode || player.experienceLevel >= 3);
            }

            public ItemStack onTake(EntityPlayer player, ItemStack stack) {
                ItemStack resultTool = resultSlotTool.getStackInSlot(0).copy();
                if (!resultTool.isEmpty()) {
                    if (!player.capabilities.isCreativeMode) {
                        player.addExperienceLevel(-3);
                    }

                    // put the result tool into the tool input slot
                    inputSlots.setInventorySlotContents(false, 0, resultTool);

                    // set result tool to air
                    resultSlotTool.setInventorySlotContents(0, ItemStack.EMPTY);

                    // decrease the amount of blank books
                    ItemStack books = inputSlots.getStackInSlot(1).copy();
                    if (books.getCount() < 2) {
                        inputSlots.setInventorySlotContents(false, 1, ItemStack.EMPTY);
                    } else {
                        books.setCount(books.getCount() - 1);
                        inputSlots.setInventorySlotContents(false, 1, books);
                    }

                    NEW_INPUT_TOOL = true;
                    new Thread(() -> {
                        try {
                            TimeUnit.SECONDS.sleep(5);
                        } catch (InterruptedException ignore) {
                        }
                        NEW_INPUT_TOOL = false;
                    }).start();
                    new Thread(() -> {
                        try {
                            TimeUnit.MILLISECONDS.sleep(50);
                        } catch (InterruptedException ignore) {
                        }
                        onCraftMatrixChanged(inputSlots);
                    }).start();
                }
                return stack;
            }
        });

        // player inventory
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        // player hotbar
        for (int k = 0; k < 9; ++k) {
            addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    public void onCraftMatrixChanged(IInventory inventoryIn) {
        super.onCraftMatrixChanged(inventoryIn);

        if (inventoryIn != inputSlots) {
            return;
        }

        ItemStack toolStack = inputSlots.getStackInSlot(0).copy();
        ItemStack bookStack = inputSlots.getStackInSlot(1).copy();
        if (toolStack.isEmpty() || bookStack.isEmpty()) {
            NEW_INPUT_TOOL = false;
            clearFlags();
            clearResults();
            return; // at least one input slot is empty
        }

        if (bookStack.isItemEnchanted() || bookStack.getItem() != Items.BOOK) {
            clearFlags();
            BAD_INPUT_BOOK = true;
            clearResults();
            return; // not a regular book
        }

        Map<Enchantment, Integer> toolEnchants = EnchantmentHelper.getEnchantments(toolStack);
        if (toolEnchants.size() < 2) {
            clearFlags();
            BAD_INPUT_TOOL = true;
            clearResults();
            return; // not multiple enchants
        }

        Enchantment enchantment = null;
        int level = -1;
        int index = ThreadLocalRandom.current().nextInt(toolEnchants.size());

        int i = 0;
        for (Map.Entry<Enchantment, Integer> entry : toolEnchants.entrySet()) {
            if (i == index) {
                enchantment = entry.getKey();
                level = entry.getValue();
                break;
            }
            i++;
        }

        if (enchantment == null || level < 0) {
            clearFlags();
            clearResults();
            return; // something went wrong picking random enchant
        }

        toolEnchants.remove(enchantment);

        // set the results
        ItemStack resultTool = toolStack.copy();
        ItemStack resultBook = new ItemStack(Items.ENCHANTED_BOOK, 1);

        if (toolStack.getItem() == Items.ENCHANTED_BOOK) {
            ItemStack newTool = new ItemStack(Items.ENCHANTED_BOOK, 1);
            toolEnchants.forEach((k, v) -> ItemEnchantedBook.addEnchantment(newTool, new EnchantmentData(k, v)));
            resultTool = newTool;
        } else {
            EnchantmentHelper.setEnchantments(toolEnchants, resultTool);
        }
        ItemEnchantedBook.addEnchantment(resultBook, new EnchantmentData(enchantment, level));

        resultSlotTool.setInventorySlotContents(0, resultTool);
        resultSlotBook.setInventorySlotContents(1, resultBook);

        clearFlags();
        detectAndSendChanges();
    }

    private void clearResults() {
        resultSlotTool.setInventorySlotContents(0, ItemStack.EMPTY);
        resultSlotBook.setInventorySlotContents(1, ItemStack.EMPTY);
    }

    private void clearFlags() {
        BAD_INPUT_TOOL = false;
        BAD_INPUT_BOOK = false;
    }

    public void addListener(IContainerListener listener) {
        super.addListener(listener);
    }

    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
        if (!world.isRemote) {
            clearContainer(playerIn, world, inputSlots);
        }
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        // check if correct block type & make sure player is within 8 blocks distance
        return world.getBlockState(selfPosition).getBlock() == ModBlocks.enchantmentSplitter &&
                playerIn.getDistanceSq((double) selfPosition.getX() + 0.5D,
                        (double) selfPosition.getY() + 0.5D,
                        (double) selfPosition.getZ() + 0.5D) <= 64.0D;
    }

    // handle shift clicking
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        Slot slot = inventorySlots.get(index);
        if (slot == null || !slot.getHasStack()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getStack();
        ItemStack copy = stack.copy();

        // shift clicked a result slot
        if (index == 2 || index == 3) {
            if (!mergeItemStack(stack, 4, 40, true)) {
                return ItemStack.EMPTY;
            }
            slot.onSlotChange(stack, copy);
        }

        // shift clicked player inventory slot
        else if (index != 0 && index != 1) {
            if (index < 40 && !mergeItemStack(stack, 0, 3, false)) {
                return ItemStack.EMPTY;
            }
        }

        // shift clicked an input slot
        else if (!mergeItemStack(stack, 4, 40, false)) {
            return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) {
            slot.putStack(ItemStack.EMPTY);
        } else {
            slot.onSlotChanged();
        }

        if (stack.getCount() == copy.getCount()) {
            return ItemStack.EMPTY;
        }

        slot.onTake(player, stack);
        return copy;
    }
}
