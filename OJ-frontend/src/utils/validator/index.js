const EMAIL_REGEX = /^[A-Za-z0-9]+([_\.][A-Za-z0-9]+)*@([A-Za-z0-9\-]+\.)+[A-Za-z]{2,6}$/

const PHONE_REGEX = /^1\d{10}$/

export const validateEmail = function (str) {
  return EMAIL_REGEX.test(str)
}

export const validatePhone = function (str) {
  return PHONE_REGEX.test(str)
}
