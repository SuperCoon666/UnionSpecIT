<!-- <template>
    <div>
        <el-dialog title="Settings" :visible.sync="dialogVisible" width="30%" >
            <el-form label-position="top" >
                <el-form-item label="Theme Color">
                    <el-select v-model="selectedColor" placeholder="Select a color" style="color: #84fac5; !important">
                        <el-option
                            v-for="(item, index) in themeColors"
                            :key="index"
                            :label="item"
                            :value="item">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="Font Size">
                    <el-slider v-model="fontSize" :min="12" :max="24"></el-slider>
                </el-form-item>
                <el-form-item label="Enable Dark Mode">
                    <el-switch v-model="darkMode" @change="updateTheme"></el-switch>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template> -->