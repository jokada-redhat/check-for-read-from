# check-for-read-from

## build app

```sh
./mvnw clean quarkus:build
```

```sh
oc new-app java:openjdk-17-ubi8~https://github.com/jokada-redhat/check-for-read-from.git
oc create route edge --service check-for-read-from --insecure-policy=Redirect
```

## test 1 - no override values

### run app

```sh
podman run -it --rm -p 8080:8080 \
    -e DUMMY_FILE=/etc/bashrc \
    -e DUMMY_ENV=override_value1 \
    -v ./target/quarkus-app:/deployments \
    registry.access.redhat.com/ubi9/openjdk-21-runtime
```
### check env

* request

    ```sh
    curl localhost:8080/read-from/env 
    ```

* response

    ```txt
    default_value
    ```

### check file

* request

    ```sh
    curl localhost:8080/read-from/file
    ```

* response

    ```txt
    NAME="Red Hat Enterprise Linux"
    VERSION="9.4 (Plow)"
    ID="rhel"
    ID_LIKE="fedora"
    VERSION_ID="9.4"
    PLATFORM_ID="platform:el9"
    PRETTY_NAME="Red Hat Enterprise Linux 9.4 (Plow)"
    ANSI_COLOR="0;31"
    LOGO="fedora-logo-icon"
    CPE_NAME="cpe:/o:redhat:enterprise_linux:9::baseos"
    HOME_URL="https://www.redhat.com/"
    DOCUMENTATION_URL="https://access.redhat.com/documentation/en-us/red_hat_enterprise_linux/9"
    BUG_REPORT_URL="https://bugzilla.redhat.com/"

    REDHAT_BUGZILLA_PRODUCT="Red Hat Enterprise Linux 9"
    REDHAT_BUGZILLA_PRODUCT_VERSION=9.4
    REDHAT_SUPPORT_PRODUCT="Red Hat Enterprise Linux"
    REDHAT_SUPPORT_PRODUCT_VERSION="9.4"
    ```

## test 2 - override values

### run app

```sh
podman run -it --rm -p 8080:8080 \
    -e DUMMY_FILE=/etc/bashrc \
    -e DUMMY_ENV=override_value1 \
    -v ./target/quarkus-app:/deployments \
    registry.access.redhat.com/ubi9/openjdk-21-runtime
```
### check env

* request

    ```sh
    curl localhost:8080/read-from/env 
    ```

* response

    ```txt
    override_value1
    ```

### check file

* request

    ```sh
    curl localhost:8080/read-from/file
    ```

* response

    ```txt
    # /etc/bashrc

    # System wide functions and aliases
    # Environment stuff goes in /etc/profile

    # It's NOT a good idea to change this file unless you know what you
    # are doing. It's much better to create a custom.sh shell script in
    # /etc/profile.d/ to make custom changes to your environment, as this
    # will prevent the need for merging in future updates.

    # Prevent doublesourcing
    if [ -z "$BASHRCSOURCED" ]; then
    BASHRCSOURCED="Y"

    # are we an interactive shell?
    if [ "$PS1" ]; then
        if [ -z "$PROMPT_COMMAND" ]; then
        case $TERM in
        xterm*|vte*)
            if [ -e /etc/sysconfig/bash-prompt-xterm ]; then
                PROMPT_COMMAND=/etc/sysconfig/bash-prompt-xterm
            else
                PROMPT_COMMAND='printf "\033]0;%s@%s:%s\007" "${USER}" "${HOSTNAME%%.*}" "${PWD/#$HOME/\~}"'
            fi
            ;;
        screen*)
            if [ -e /etc/sysconfig/bash-prompt-screen ]; then
                PROMPT_COMMAND=/etc/sysconfig/bash-prompt-screen
            else
                PROMPT_COMMAND='printf "\033k%s@%s:%s\033\\" "${USER}" "${HOSTNAME%%.*}" "${PWD/#$HOME/\~}"'
            fi
            ;;
        *)
            [ -e /etc/sysconfig/bash-prompt-default ] && PROMPT_COMMAND=/etc/sysconfig/bash-prompt-default
            ;;
        esac
        fi
        # Turn on parallel history
        shopt -s histappend
        history -a
        # Turn on checkwinsize
        shopt -s checkwinsize
        [ "$PS1" = "\\s-\\v\\\$ " ] && PS1="[\u@\h \W]\\$ "
        # You might want to have e.g. tty in prompt (e.g. more virtual machines)
        # and console windows
        # If you want to do so, just add e.g.
        # if [ "$PS1" ]; then
        #   PS1="[\u@\h:\l \W]\\$ "
        # fi
        # to your custom modification shell script in /etc/profile.d/ directory
    fi

    if ! shopt -q login_shell ; then # We're not a login shell
        # Need to redefine pathmunge, it gets undefined at the end of /etc/profile
        pathmunge () {
            case ":${PATH}:" in
                *:"$1":*)
                    ;;
                *)
                    if [ "$2" = "after" ] ; then
                        PATH=$PATH:$1
                    else
                        PATH=$1:$PATH
                    fi
            esac
        }

        # Set default umask for non-login shell only if it is set to 0
        [ `umask` -eq 0 ] && umask 022

        SHELL=/bin/bash
        # Only display echos from profile.d scripts if we are no login shell
        # and interactive - otherwise just process them to set envvars
        for i in /etc/profile.d/*.sh; do
            if [ -r "$i" ]; then
                if [ "$PS1" ]; then
                    . "$i"
                else
                    . "$i" >/dev/null
                fi
            fi
        done

        unset i
        unset -f pathmunge
    fi

    fi
    # vim:ts=4:sw=4
    ```
