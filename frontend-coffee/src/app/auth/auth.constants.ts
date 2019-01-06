//perhaps in future i would like to have multiple roles per user so for futurepruff reasons I kept this array of GrantedAuthorities
export const AuthConstants = {
    userRoles : [
        {
            desc: 'Engineer - Main user of coffee',
            value: [{
                name : 'ROLE_ENGINEER'
            }]
        },
        {
            desc: 'Bad manager - Not allowed to use coffe',
            value: [{
                name : 'ROLE_BAD_MANAGER'
            }]
        },
        {
            desc: 'Administrator - Can do anything',
            value: [{
                name : 'ROLE_ADMIN'
            }]
        }
    ]
}