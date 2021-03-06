package buildcraft.transport.client.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import buildcraft.core.lib.client.model.IModelCache;
import buildcraft.core.lib.client.model.ModelCacheJoiner;
import buildcraft.core.lib.client.model.ModelCacheJoiner.ModelKeyWrapper;
import buildcraft.transport.Pipe;
import buildcraft.transport.PipePluggableState;
import buildcraft.transport.PipeRenderState;
import buildcraft.transport.client.model.PipeModelCacheBase.PipeBaseCutoutKey;
import buildcraft.transport.client.model.PipeModelCacheBase.PipeBaseTransclucentKey;

public class PipeModelCacheAll {
    private static final IModelCache<PipeAllCutoutKey> cacheCutout;
    private static final IModelCache<PipeAllTranslucentKey> cacheTranslucent;

    static {
        List<ModelKeyWrapper<PipeAllCutoutKey, ?>> cutout = new ArrayList<>();
        cutout.add(new ModelKeyWrapper<>(PipeAllCutoutKey::getBaseCutout, PipeModelCacheBase.cacheCutout));
        // TODO: Pluggables!
        cacheCutout = new ModelCacheJoiner<>("pipe.all.cutout", cutout);

        List<ModelKeyWrapper<PipeAllTranslucentKey, ?>> translucent = new ArrayList<>();
        translucent.add(new ModelKeyWrapper<>(PipeAllTranslucentKey::getBaseTranslucent, PipeModelCacheBase.cacheTranslucent));
        // TODO: Pluggables!
        cacheTranslucent = new ModelCacheJoiner<>("pipe.all.transclucent", translucent);
    }

    public static ImmutableList<BakedQuad> getCutoutModel(Pipe<?> pipe, PipeRenderState render, PipePluggableState pluggable) {
        PipeAllCutoutKey key = new PipeAllCutoutKey(pipe, render, pluggable);
        return cacheCutout.bake(key, DefaultVertexFormats.BLOCK);
    }

    public static ImmutableList<BakedQuad> getTranslucentModel(Pipe<?> pipe, PipeRenderState render, PipePluggableState pluggable) {
        PipeAllTranslucentKey key = new PipeAllTranslucentKey(pipe, render, pluggable);
        return cacheTranslucent.bake(key, DefaultVertexFormats.BLOCK);
    }

    public static class PipeAllCutoutKey {
        private final PipeBaseCutoutKey cutout;
        // TODO: Pluggable key!
        private final int hash;

        public PipeAllCutoutKey(Pipe<?> pipe, PipeRenderState render, PipePluggableState pluggable) {
            cutout = new PipeBaseCutoutKey(pipe, render);
            // TODO: Pluggable key!
            hash = Objects.hash(cutout);
        }

        public static PipeBaseCutoutKey getBaseCutout(PipeAllCutoutKey key) {
            return key.cutout;
        }

        @Override
        public int hashCode() {
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            PipeAllCutoutKey other = (PipeAllCutoutKey) obj;
            if (cutout == null) {
                if (other.cutout != null) return false;
            } else if (!cutout.equals(other.cutout)) return false;
            return true;
        }

        @Override
        public String toString() {
            return "PipeAllCutoutKey [base=" + cutout + "]";
        }
    }

    public static class PipeAllTranslucentKey {
        private final PipeBaseTransclucentKey translucent;
        // TODO: Pluggable key!
        private final int hash;

        public PipeAllTranslucentKey(Pipe<?> pipe, PipeRenderState render, PipePluggableState pluggable) {
            translucent = new PipeBaseTransclucentKey(render);
            // TODO: Pluggable key!
            hash = Objects.hash(translucent);
        }

        public static PipeBaseTransclucentKey getBaseTranslucent(PipeAllTranslucentKey key) {
            return key.translucent;
        }

        @Override
        public int hashCode() {
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            PipeAllTranslucentKey other = (PipeAllTranslucentKey) obj;
            if (translucent == null) {
                if (other.translucent != null) return false;
            } else if (!translucent.equals(other.translucent)) return false;
            return true;
        }

        @Override
        public String toString() {
            return "PipeAllTranslucentKey [base=" + translucent + "]";
        }
    }
}
