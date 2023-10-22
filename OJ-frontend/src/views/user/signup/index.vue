<template>
  <CommonPage show-footer :showHeader="false">
    <div class="f-c-c w-full">
      <div class="max-w-900 w-full">
        <n-form  ref="form" :model="form" :rules="rules" class="grid md:grid-cols-2 md:gap-x-30">
          <n-form-item label="选择学校" prop="school" :rules="[{ required: true, message: '请选择学校', trigger: 'blur' }]">
            <n-select v-model:value="form.school"  filterable placeholder="请选择学校" :options="schoolOptions"/>
          </n-form-item>
          <n-form-item label="学号" prop="studentId" :rules="[{ required: true, message: '请输入学号', trigger: 'blur' }]">
            <n-input v-model="form.studentId" placeholder="请输入学号"></n-input>
          </n-form-item>
          <n-form-item label="姓名">
            <n-input v-model="form.name" placeholder="请输入姓名"></n-input>
          </n-form-item>
          <n-form-item label="班级">
            <n-input v-model="form.class" placeholder="请输入班级"></n-input>
          </n-form-item>
          <n-form-item label="邮箱" prop="email" :rules="[{ required: true, message: '请输入邮箱', trigger: 'blur' }, { type: 'email', message: '请输入有效的邮箱', trigger: ['blur', 'change'] }]">
            <n-input v-model="form.email" placeholder="请输入邮箱"></n-input>
          </n-form-item>
          <n-form-item label="手机号" prop="phone" :rules="[{ required: true, message: '请输入手机号', trigger: 'blur' }, { pattern: /^1\d{10}$/, message: '请输入有效的手机号', trigger: ['blur', 'change'] }]">
            <n-input v-model="form.phone" placeholder="请输入手机号"></n-input>
          </n-form-item>
          <n-form-item label="密码">
            <n-input v-model="form.password" placeholder="请输入密码" type="password"></n-input>
          </n-form-item>
          <n-form-item label="确认密码" prop="confirmPassword" :rules="[{ required: true, message: '请确认密码', trigger: 'blur' }, { validator: validatePassword, trigger: 'blur' }]">
            <n-input v-model="form.confirmPassword" placeholder="请确认密码" type="password"></n-input>
          </n-form-item>
          <n-form-item label="问题">
            <n-input v-model="form.question" placeholder="请输入问题"></n-input>
          </n-form-item>
          <n-form-item label="答案">
            <n-input v-model="form.answer" placeholder="请输入答案"></n-input>
          </n-form-item>
          <n-form-item>
            <n-button type="primary" @click="submitForm">注册</n-button>
          </n-form-item>
        </n-form>
      </div>
    </div>
  </CommonPage>
</template>

<script setup>
import { ref } from 'vue';

import schools from './school.json'

const form = ref({
  school: '',
  studentId: '',
  name: '',
  class: '',
  email: '',
  phone: '',
  password: '',
  confirmPassword: '',
  question: '',
  answer: '',
});

const schoolOptions = schools.map(item=>(
  {
    label: item.name,
    value: item.name
  }
))

const rules = ref({
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' },
  ],
});

const validatePassword = (rule, value, callback) => {
  if (value !== form.value.password) {
    callback(new Error('两次输入的密码不一致'));
  } else {
    callback();
  }
};

const submitForm = () => {
  const { form } = refs;
  form.validate((valid) => {
    if (valid) {
      // 表单验证通过，可以提交数据
      console.log('表单验证通过，提交数据', form.model);
    } else {
      console.log('表单验证失败');
    }
  });
};
</script>

