package com.xkball.flamereaction.capability.fluid;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

public abstract class SimpleFluidHandler implements IFluidHandler {
    
//    @Nonnull
//    protected FluidStack fluid = FluidStack.EMPTY;
    protected int capacity;
    
    public SimpleFluidHandler(int capacity) {
        this.capacity = capacity;
    }
    
    public SimpleFluidHandler setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }
    
    @Override
    public int getTanks() {
        return 1;
    }
    
    @NotNull
    @Override
    public abstract FluidStack getFluidInTank(int tank);
    
    @Override
    public int getTankCapacity(int tank) {
        return tank==0?capacity:0;
    }
    
    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return true;
    }
    
    //复制自FluidTank类
    @Override
    public int fill(FluidStack resource, FluidAction action) {
        var fluid = getFluidInTank(0);
        if (resource.isEmpty() || !isFluidValid(0,resource))
        {
            return 0;
        }
        if (action.simulate())
        {
            if (fluid.isEmpty())
            {
                return Math.min(capacity, resource.getAmount());
            }
            if (!getFluidInTank(0).isFluidEqual(resource))
            {
                return 0;
            }
            return Math.min(capacity - fluid.getAmount(), resource.getAmount());
        }
        if (fluid.isEmpty())
        {
            fluid = new FluidStack(resource, Math.min(capacity, resource.getAmount()));
            setFluidInTank(0,fluid);
            onChanged();
            return fluid.getAmount();
        }
        if (!fluid.isFluidEqual(resource))
        {
            return 0;
        }
        int filled = capacity - fluid.getAmount();
    
        if (resource.getAmount() < filled)
        {
            fluid.grow(resource.getAmount());
            filled = resource.getAmount();
        }
        else
        {
            fluid.setAmount(capacity);
        }
        if (filled > 0)
            onChanged();
        return filled;
    }
    
    @NotNull
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        if (resource.isEmpty() || !resource.isFluidEqual(getFluidInTank(0)))
        {
            return FluidStack.EMPTY;
        }
        return drain(resource.getAmount(), action);
    }
    
    @NotNull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        int drained = maxDrain;
        var fluid = getFluidInTank(0);
        if (fluid.getAmount() < drained)
        {
            drained = fluid.getAmount();
        }
        FluidStack stack = new FluidStack(fluid, drained);
        if (action.execute() && drained > 0)
        {
            fluid.shrink(drained);
            onChanged();
        }
        return stack;
    }
    
    protected abstract void setFluidInTank(int shot,FluidStack fluidStack);
    
    public void onChanged(){
    
    }
}
