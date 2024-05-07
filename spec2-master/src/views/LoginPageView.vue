<template>
    <div class="child-personal-account-login">
        <el-form :model="loginForm" :rules="loginRules" ref="loginForm" @submit.native.prevent="handleLogin" >
            <el-form-item class="login-title">
                <h3 class="text-login-param">Login</h3>
            </el-form-item>
            <el-form-item prop="phone">
                <el-input prefix-icon="el-icon-message" placeholder="Email" ref="login" v-model="loginForm.phone"
                ></el-input>
            </el-form-item>
            <el-form-item prop="pass">
                <el-input class="input-param" prefix-icon="el-icon-key" type="password" placeholder="Password" v-model="loginForm.pass" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button class="text-but-parm" type="primary" @click.native.prevent="handleLogin" :loading="loading">Sign in</el-button>
            </el-form-item>
            <el-form-item>
                <el-button class="text-but-parm" type="primary" @click.native.prevent="handleRegister" :loading="loading">Sign up</el-button>
            </el-form-item>
        </el-form>
        <div class="asset">
            <img src="../assets/pleased_racoon.webp" />
        </div>
    </div>
</template>

<script>
import { validateEmail } from '@/utils/validate'
import axios from 'axios';
    export default {
    name: 'Login',
    data() {
        const validateEmailWrapper = (rule, value, callback) => {
            // console.log('value', value)
            if (!validateEmail(value)) {
                callback(new Error('Incorrect email'))
            } else {
                callback()
            }
        }
        const validatePassWrapper = (rule, value, callback) => {
            // console.log('value', value)
            if (value.length < 15) {
                callback(new Error('Password must be at least 15 characters'))
            } else {
                callback()
            }
        }

        return {
            loginForm: {
                phone: '',
                pass: ''
            },
            loginRules: {
                phone: [{ required: true, trigger: 'change', validator: validateEmailWrapper }],
                pass: [{required: true, trigger: 'change', validator: validatePassWrapper}]
            },
            passwordType: 'password',
            loading: false,
            showDialog: false,
            redirect: undefined,
            loginFormVisible: true,
            recoveryFormVisible: false,
            successRecoveryRequestVisible: false
        }
    },
    created() {
        this.$nextTick(() => {
            this.$refs.login.focus()
        })
    },
    methods: {
        handleLogin() {
            this.$refs.loginForm.validate(valid => {
                if (valid) {
                    this.loading = true;
                    axios.post('http://localhost:8081/login', {
                        nickname: this.loginForm.phone, // Используем phone как nickname
                        pswrd: this.loginForm.pass
                    })
                    .then(response => {
                        console.log('Login result:', response.data);
                        // Здесь response.data предполагается содержать объект с id и nickname
                        const { id, nickname } = response.data;
                        this.$store.commit('setUserId', id);
                        this.$store.commit('setUserNickname', nickname);

                        this.$router.push({
                            name: 'home'
                        });
                    })
                    .catch(error => {
                        console.error('Login failed:', error.response.data);
                        // TODO: обработка ошибки логина
                    })
                    .finally(() => {
                        this.loading = false;
                    });
                } else {
                    console.error('Validation failed');
                    // Форма не валидна, не отправляем запрос
                }
            });
        },
        handleRegister() {
            this.$refs.loginForm.validate(valid => {
                if (valid) {
                    this.loading = true;
                    axios.post('http://localhost:8081/register', { // Обратите внимание на измененный URL
                        nickname: this.loginForm.phone, // Используем phone как nickname
                        pswrd: this.loginForm.pass
                    })
                    .then(response => {
                        console.log('Registration result:', response.data);

                        this.$router.push({
                            name: 'home'
                        });
                    })
                    .catch(error => {
                        console.error('Registration failed:', error.response.data);
                        // TODO: обработка ошибки регистрации
                    })
                    .finally(() => {
                        this.loading = false;
                    });
                } else {
                    console.error('Validation failed');
                    // Форма не валидна, не отправляем запрос на регистрацию
                }
            });
        }
    }
  }
</script>

<style lang="scss">
    .child-personal-account-login {
        box-sizing: border-box;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        min-height: 100vh;
        width: 100%;
        text-align: center;

        .asset {
            margin-top: 150px;
            position: relative;
            display: inline-block;
            left: -130px;
        }

        .text-login-param{
            color: #42b983 !important;
        }
        .text-but-parm{
            color: #42b983 !important;
            background-color: #485656 !important;
            border-color: #485656 !important;
        }
        .input-param {
            background-color: #485656 !important;
            border-color: #485656 !important;
        }

        .el-form {
            width: 400px;
            padding: 10px;

            h3 {
                color: #303133;
                width: 100%;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
            }

            .el-form-item {
                margin-bottom: 1rem;
            }

            .el-input__prefix {
                font-size: 20px;
                left: 10px;
            }

            .el-input__inner {
                padding-left: 40px !important;
            }

            input::placeholder {
                font-weight: normal;
            }

            .el-button {
                width: 100%;
            }

            .form-footer {
                display: flex;
                align-items: center;

                .forgot-password-label {
                    color: #C6C6C6;

                    &:hover {
                        border-bottom: 1px solid #C6C6C6;
                        cursor: pointer;
                    }
                }
            }
        }

        .login-title {
            margin-bottom: 1rem;
            margin-top: 50px;

            h3 {
                color: #42b983;
                margin: 0;
            }
        }

    }

    @media (max-width: 800px){
        .child-personal-account-login{
            .el-form {
                width: 100%;
            }
        }
    }

    @media (max-width: 560px){
        .child-personal-account-login{
            .el-form {
                width: 100%;
            }
        }
    }

    @media (max-width: 480px) {
        .child-personal-account-login {
            width: 100%;
            padding: 0 20px;
        }

        .el-form {
            width: 100%;
        }
    }
</style>

