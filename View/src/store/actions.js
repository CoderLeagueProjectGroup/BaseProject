import { getUsername } from '../service/getData';
export default {
    async getUsername({ commit, state }) {
        let res = await getUsername();
        commit('setUsername', res);
    }
}