package us.graymatterapps.fastxporbs.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbEntityMixin extends Entity{
    @Shadow private PlayerEntity target;

    public ExperienceOrbEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo ci){
        if(this.target != null){
            this.setPosition(this.target.getPos());
        }
    }

    @Inject(at = @At("TAIL"), method = "expensiveUpdate")
    private void expensiveUpdate(CallbackInfo ci){
        if (this.target == null || this.target.squaredDistanceTo(this) > 64.0) {
            this.target = this.getWorld().getClosestPlayer(this, 16.0);
        }
    }

    @Override
    public void initDataTracker(DataTracker.Builder builder) {

    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {

    }
}
