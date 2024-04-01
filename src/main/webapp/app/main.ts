// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.common with an alias.
import Vue, { createApp, provide, computed } from 'vue';
import { createPinia } from 'pinia';

import App from './app.vue';
import router from './router';
import { initFortAwesome } from './shared/config/config';
import { initBootstrapVue } from './shared/config/config-bootstrap-vue';
import JhiItemCountComponent from './shared/jhi-item-count.vue';
import JhiSortIndicatorComponent from './shared/sort/jhi-sort-indicator.vue';
import LoginService from './account/login.service';
import AccountService from './account/account.service';
import { setupAxiosInterceptors } from '@/shared/config/axios-interceptor';
import { useStore } from '@/store';

import '../content/scss/global.scss';
import '../content/scss/vendor.scss';

const pinia = createPinia();

// jhipster-needle-add-entity-service-to-main-import - JHipster will import entities services here

initBootstrapVue(Vue);

Vue.configureCompat({
  MODE: 2,
  ATTR_FALSE_VALUE: 'suppress-warning',
  COMPONENT_FUNCTIONAL: 'suppress-warning',
  COMPONENT_V_MODEL: 'suppress-warning',
  CONFIG_OPTION_MERGE_STRATS: 'suppress-warning',
  CONFIG_WHITESPACE: 'suppress-warning',
  CUSTOM_DIR: 'suppress-warning',
  GLOBAL_EXTEND: 'suppress-warning',
  GLOBAL_MOUNT: 'suppress-warning',
  GLOBAL_PRIVATE_UTIL: 'suppress-warning',
  GLOBAL_PROTOTYPE: 'suppress-warning',
  GLOBAL_SET: 'suppress-warning',
  INSTANCE_ATTRS_CLASS_STYLE: 'suppress-warning',
  INSTANCE_CHILDREN: 'suppress-warning',
  INSTANCE_DELETE: 'suppress-warning',
  INSTANCE_DESTROY: 'suppress-warning',
  INSTANCE_EVENT_EMITTER: 'suppress-warning',
  INSTANCE_EVENT_HOOKS: 'suppress-warning',
  INSTANCE_LISTENERS: 'suppress-warning',
  INSTANCE_SCOPED_SLOTS: 'suppress-warning',
  INSTANCE_SET: 'suppress-warning',
  OPTIONS_BEFORE_DESTROY: 'suppress-warning',
  OPTIONS_DATA_MERGE: 'suppress-warning',
  OPTIONS_DESTROYED: 'suppress-warning',
  RENDER_FUNCTION: 'suppress-warning',
  WATCH_ARRAY: 'suppress-warning',
});

const app = createApp({
  compatConfig: { MODE: 3 },
  components: { App },
  template: '<App/>',
  setup(_props, { emit }) {
    const loginService = new LoginService({ emit });
    provide('loginService', loginService);
    const store = useStore();
    const accountService = new AccountService(store);
    provide(
      'currentLanguage',
      computed(() => store.account?.langKey ?? navigator.language ?? 'en'),
    );

    router.beforeResolve(async (to, from, next) => {
      // Make sure login modal is closed
      loginService.hideLogin();

      if (!store.authenticated) {
        await accountService.update();
      }
      if (to.meta?.authorities && to.meta.authorities.length > 0) {
        const value = await accountService.hasAnyAuthorityAndCheckAuth(to.meta.authorities);
        if (!value) {
          if (from.path !== '/forbidden') {
            next({ path: '/forbidden' });
            return;
          }
        }
      }
      next();
    });

    setupAxiosInterceptors(
      error => {
        const url = error.response?.config?.url;
        const status = error.status || error.response.status;
        if (status === 401) {
          // Store logged out state.
          store.logout();
          if (!url.endsWith('api/account') && !url.endsWith('api/authenticate')) {
            // Ask for a new authentication
            loginService.openLogin();
            return;
          }
        }
        return Promise.reject(error);
      },
      error => {
        return Promise.reject(error);
      },
    );

    provide(
      'authenticated',
      computed(() => store.authenticated),
    );
    provide(
      'currentUsername',
      computed(() => store.account?.login),
    );

    provide('accountService', accountService);
    // jhipster-needle-add-entity-service-to-main - JHipster will import entities services here
  },
});

initFortAwesome(app);

app
  .component('jhi-item-count', JhiItemCountComponent)
  .component('jhi-sort-indicator', JhiSortIndicatorComponent)
  .use(router)
  .use(pinia)
  .mount('#app');
