export function validateEmail(email) {
    const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    return re.test(email)
}

export function validatePhone(phone) {
    try {
        for (const char of phone) {
            if (!(/^\d$/.test(char) || '()-+ '.includes(char))) {
                return false
            }
        }
        const phoneUtil = require('google-libphonenumber').PhoneNumberUtil.getInstance()
        const number = phoneUtil.parseAndKeepRawInput(phone, 'RU')
        console.log('number:', number)
        console.log('isValidNumber:', phoneUtil.isValidNumber(number))
        return phoneUtil.isValidNumber(number)
    } catch (error) {
        return false
    }
}
