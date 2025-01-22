package net.ironingot.horseinforeloaded.fabric.render_state.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ironingot.horseinforeloaded.fabric.render_state.HorseWithInfoRenderState;
import net.minecraft.client.model.AbstractEquineModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class DonkeyWithInfoModel extends AbstractEquineModel<HorseWithInfoRenderState> {
    private final ModelPart leftChest;
    private final ModelPart rightChest;

    public DonkeyWithInfoModel(ModelPart modelPart) {
        super(modelPart);
        leftChest = body.getChild("left_chest");
        rightChest = body.getChild("right_chest");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition bodyMesh = AbstractEquineModel.createBodyMesh(CubeDeformation.NONE);
        modifyMesh(bodyMesh.getRoot());
        return LayerDefinition.create(bodyMesh, 64, 64);
    }

    public static LayerDefinition createBabyLayer() {
        MeshDefinition babyMesh = AbstractEquineModel.createFullScaleBabyMesh(CubeDeformation.NONE);
        modifyMesh(babyMesh.getRoot());
        return LayerDefinition.create(AbstractEquineModel.BABY_TRANSFORMER.apply(babyMesh), 64, 64);
    }

    private static void modifyMesh(@NotNull PartDefinition root) {
        PartDefinition body = root.getChild("body");
        CubeListBuilder chest = CubeListBuilder.create().texOffs(26, 21).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 8.0F, 3.0F);
        body.addOrReplaceChild("left_chest", chest, PartPose.offsetAndRotation(6.0F, -8.0F, 0.0F, 0.0F, (-(float)Math.PI / 2F), 0.0F));
        body.addOrReplaceChild("right_chest", chest, PartPose.offsetAndRotation(-6.0F, -8.0F, 0.0F, 0.0F, ((float)Math.PI / 2F), 0.0F));
        PartDefinition head = root.getChild("head_parts").getChild("head");
        CubeListBuilder ear = CubeListBuilder.create().texOffs(0, 12).addBox(-1.0F, -7.0F, 0.0F, 2.0F, 7.0F, 1.0F);
        head.addOrReplaceChild("left_ear", ear, PartPose.offsetAndRotation(1.25F, -10.0F, 4.0F, 0.2617994F, 0.0F, 0.2617994F));
        head.addOrReplaceChild("right_ear", ear, PartPose.offsetAndRotation(-1.25F, -10.0F, 4.0F, 0.2617994F, 0.0F, -0.2617994F));
    }

    public void setupAnim(@NotNull HorseWithInfoRenderState renderState) {
        super.setupAnim(renderState);
        this.leftChest.visible = renderState.hasChest;
        this.rightChest.visible = renderState.hasChest;
    }
}