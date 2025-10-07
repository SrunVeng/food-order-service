package com.food.foodorderapi.library.utils.MailTemplateUtils;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class MailTemplates {

    public String accountCreatedEmail(
            String appName,
            String userDisplayName,
            String ctaText,
            String ctaUrl,
            String supportEmail,
            String companyName,
            String companyAddress,
            String logoUrl
    ) {
        String safeLogo = (logoUrl == null || logoUrl.isBlank())
                ? "https://via.placeholder.com/140x40?text=" + urlEncode(appName)
                : logoUrl;

        return """
                <!doctype html>
                <html lang="en" xmlns="http://www.w3.org/1999/xhtml">
                <head>
                  <meta charset="utf-8">
                  <meta http-equiv="x-ua-compatible" content="ie=edge">
                  <meta name="viewport" content="width=device-width, initial-scale=1">
                  <meta name="color-scheme" content="light dark">
                  <meta name="supported-color-schemes" content="light dark">
                  <title>Your %1$s account is ready</title>
                  <style>
                    html, body { margin:0 !important; padding:0 !important; height:100%% !important; }
                    body { width:100%% !important; -webkit-text-size-adjust:100%%; -ms-text-size-adjust:100%%; }
                    table, td { mso-table-lspace:0pt; mso-table-rspace:0pt; }
                    img { -ms-interpolation-mode:bicubic; border:0; outline:none; text-decoration:none; }
                    a { text-decoration:none; }
                    .wrapper { width:100%%; background:#f6f8fb; }
                    .container { width:100%%; max-width:640px; margin:0 auto; }
                    .card { background:#ffffff; border-radius:12px; overflow:hidden; }
                    .px { padding-left:24px; padding-right:24px; }
                    .py { padding-top:24px; padding-bottom:24px; }
                    .center { text-align:center; }
                    .muted { color:#6b7280; font-size:14px; line-height:1.6; }
                    .heading { font-size:22px; line-height:1.3; color:#111827; margin:0 0 8px 0; }
                    .body { font-size:16px; line-height:1.7; color:#1f2937; margin:0; }
                    .btn { display:inline-block; padding:14px 22px; border-radius:10px;
                           background:#111827; color:#ffffff !important; font-weight:600; }
                    .spacer-8 { height:8px; line-height:8px; font-size:8px; }
                    .spacer-16 { height:16px; line-height:16px; font-size:16px; }
                    .spacer-24 { height:24px; line-height:24px; font-size:24px; }
                    .footer-link { color:#6b7280; text-decoration:underline; }
                    @media (prefers-color-scheme: dark) {
                      .wrapper { background:#0b0f14 !important; }
                      .card { background:#0f1620 !important; }
                      .heading { color:#e5e7eb !important; }
                      .body { color:#d1d5db !important; }
                      .muted { color:#9ca3af !important; }
                      .btn { background:#2563eb !important; }
                    }
                    @media screen and (max-width: 480px) {
                      .px { padding-left:16px !important; padding-right:16px !important; }
                      .py { padding-top:20px !important; padding-bottom:20px !important; }
                      .heading { font-size:20px !important; }
                      .body { font-size:15px !important; }
                    }
                  </style>
                </head>
                <body style="background:#f6f8fb;">
                  <div style="display:none; max-height:0; overflow:hidden; opacity:0; mso-hide:all;">
                    Your %1$s account has been created successfully.
                  </div>

                  <table role="presentation" cellpadding="0" cellspacing="0" class="wrapper" width="100%%">
                    <tr>
                      <td align="center">
                        <table role="presentation" cellpadding="0" cellspacing="0" class="container">
                          <tr><td class="spacer-24">&nbsp;</td></tr>

                          <tr>
                            <td class="center">
                              <img src="%8$s" width="140" height="40" alt="%1$s" style="display:block;">
                            </td>
                          </tr>
                          <tr><td class="spacer-24">&nbsp;</td></tr>

                          <tr>
                            <td class="card px py">
                              <table role="presentation" width="100%%" cellpadding="0" cellspacing="0">
                                <tr>
                                  <td>
                                    <h1 class="heading">You’re all set, %2$s 🎉</h1>
                                    <p class="body">
                                      Your <strong>%1$s</strong> account was created successfully. You can sign in
                                      and start using the app right away.
                                    </p>
                                  </td>
                                </tr>

                                <tr><td class="spacer-16">&nbsp;</td></tr>

                                <tr>
                                  <td class="center">
                                    <table role="presentation" cellpadding="0" cellspacing="0" align="center">
                                      <tr>
                                        <td bgcolor="#111827" style="border-radius:10px;">
                                          <a class="btn" href="%4$s" target="_blank" rel="noopener">
                                            %3$s
                                          </a>
                                        </td>
                                      </tr>
                                    </table>
                                  </td>
                                </tr>

                                <tr><td class="spacer-16">&nbsp;</td></tr>

                                <tr>
                                  <td>
                                    <p class="muted">
                                      Having trouble with the button? Paste this link in your browser:
                                      <br>
                                      <a href="%4$s" style="word-break:break-all; color:#2563eb;">%4$s</a>
                                    </p>
                                  </td>
                                </tr>

                                <tr><td class="spacer-24">&nbsp;</td></tr>

                                <tr>
                                  <td>
                                    <p class="body" style="margin:0;">
                                      If you didn’t create this account, please reply or contact
                                      <a href="mailto:%5$s" style="color:#2563eb;">%5$s</a> so we can help.
                                    </p>
                                  </td>
                                </tr>
                              </table>
                            </td>
                          </tr>

                          <tr><td class="spacer-24">&nbsp;</td></tr>
                          <tr>
                            <td class="px">
                              <table role="presentation" width="100%%" cellpadding="0" cellspacing="0">
                                <tr>
                                  <td class="muted" style="font-size:12px;">
                                    <div class="spacer-8"></div>
                                    This message confirms a new %1$s account was created for this address.
                                    <div class="spacer-8"></div>
                                    %6$s • %7$s
                                  </td>
                                </tr>
                                <tr><td class="spacer-24">&nbsp;</td></tr>
                              </table>
                            </td>
                          </tr>

                        </table>
                      </td>
                    </tr>
                  </table>
                </body>
                </html>
                """.formatted(
                safe(appName),
                safe(userDisplayName),
                safe(ctaText),
                safe(ctaUrl),
                safe(supportEmail),
                safe(companyName),
                safe(companyAddress),
                safeLogo
        );
    }

    public String otpEmail(
            String appName,
            String otpCode,
            String supportEmail,
            String companyName,
            String companyAddress,
            String logoUrl
    ) {
        String safeLogo = (logoUrl == null || logoUrl.isBlank())
                ? "https://via.placeholder.com/140x40?text=" + urlEncode(appName)
                : logoUrl;

        return """
            <!doctype html>
            <html lang="en" xmlns="http://www.w3.org/1999/xhtml">
            <head>
              <meta charset="utf-8">
              <meta http-equiv="x-ua-compatible" content="ie=edge">
              <meta name="viewport" content="width=device-width, initial-scale=1">
              <meta name="color-scheme" content="light dark">
              <meta name="supported-color-schemes" content="light dark">
              <title>%1$s verification code</title>
              <style>
                html, body { margin:0 !important; padding:0 !important; height:100%% !important; }
                body { width:100%% !important; -webkit-text-size-adjust:100%%; -ms-text-size-adjust:100%%; }
                table, td { mso-table-lspace:0pt; mso-table-rspace:0pt; }
                img { -ms-interpolation-mode:bicubic; border:0; outline:none; text-decoration:none; }
                a { text-decoration:none; }
                .wrapper { width:100%%; background:#f6f8fb; }
                .container { width:100%%; max-width:640px; margin:0 auto; }
                .card { background:#ffffff; border-radius:12px; overflow:hidden; }
                .px { padding-left:24px; padding-right:24px; }
                .py { padding-top:24px; padding-bottom:24px; }
                .center { text-align:center; }
                .muted { color:#6b7280; font-size:14px; line-height:1.6; }
                .heading { font-size:22px; line-height:1.3; color:#111827; margin:0 0 8px 0; }
                .body { font-size:16px; line-height:1.7; color:#1f2937; margin:0; }
                .code {
                  display:inline-block; font-size:28px; font-weight:700;
                  letter-spacing:6px; padding:14px 18px; border-radius:10px;
                  background:#111827; color:#ffffff;
                }
                .spacer-8 { height:8px; line-height:8px; font-size:8px; }
                .spacer-16 { height:16px; line-height:16px; font-size:16px; }
                .spacer-24 { height:24px; line-height:24px; font-size:24px; }
                @media (prefers-color-scheme: dark) {
                  .wrapper { background:#0b0f14 !important; }
                  .card { background:#0f1620 !important; }
                  .heading { color:#e5e7eb !important; }
                  .body { color:#d1d5db !important; }
                  .muted { color:#9ca3af !important; }
                  .code { background:#2563eb !important; }
                }
                @media screen and (max-width: 480px) {
                  .px { padding-left:16px !important; padding-right:16px !important; }
                  .py { padding-top:20px !important; padding-bottom:20px !important; }
                  .heading { font-size:20px !important; }
                  .body { font-size:15px !important; }
                }
              </style>
            </head>
            <body style="background:#f6f8fb;">
              <div style="display:none; max-height:0; overflow:hidden; opacity:0; mso-hide:all;">
                Your %1$s verification code is %2$s.
              </div>

              <table role="presentation" cellpadding="0" cellspacing="0" class="wrapper" width="100%%">
                <tr>
                  <td align="center">
                    <table role="presentation" cellpadding="0" cellspacing="0" class="container">
                      <tr><td class="spacer-24">&nbsp;</td></tr>

                      <tr>
                        <td class="center">
                          <img src="%6$s" width="140" height="40" alt="%1$s" style="display:block;">
                        </td>
                      </tr>
                      <tr><td class="spacer-24">&nbsp;</td></tr>

                      <tr>
                        <td class="card px py">
                          <table role="presentation" width="100%%" cellpadding="0" cellspacing="0">
                            <tr>
                              <td>
                                <h1 class="heading">Your %1$s verification code</h1>
                                <p class="body">Enter this code to verify your email address:</p>
                              </td>
                            </tr>

                            <tr><td class="spacer-16">&nbsp;</td></tr>

                            <tr>
                              <td class="center">
                                <span class="code">%2$s</span>
                              </td>
                            </tr>

                            <tr><td class="spacer-16">&nbsp;</td></tr>

                            <tr>
                              <td>
                                <p class="muted">
                                  This code expires shortly. If you didn’t request it, you can safely ignore this email.
                                </p>
                              </td>
                            </tr>

                            <tr><td class="spacer-24">&nbsp;</td></tr>

                            <tr>
                              <td>
                                <p class="body" style="margin:0;">
                                  Need help? Contact
                                  <a href="mailto:%3$s" style="color:#2563eb;">%3$s</a>.
                                </p>
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>

                      <tr><td class="spacer-24">&nbsp;</td></tr>
                      <tr>
                        <td class="px">
                          <table role="presentation" width="100%%" cellpadding="0" cellspacing="0">
                            <tr>
                              <td class="muted" style="font-size:12px;">
                                <div class="spacer-8"></div>
                                This message was sent by %4$s.
                                <div class="spacer-8"></div>
                                %4$s • %5$s
                              </td>
                            </tr>
                            <tr><td class="spacer-24">&nbsp;</td></tr>
                          </table>
                        </td>
                      </tr>

                    </table>
                  </td>
                </tr>
              </table>
            </body>
            </html>
            """.formatted(
                safe(appName),
                safe(otpCode),
                safe(supportEmail),
                safe(companyName),
                safe(companyAddress),
                safeLogo
        );
    }

    /** NEW: reset password email moved here */
    public String resetPasswordEmail(
            String appName,
            String resetUrl,
            String supportEmail,
            String companyName,
            String companyAddress,
            String logoUrl
    ) {
        String safeLogo = (logoUrl == null || logoUrl.isBlank())
                ? "https://via.placeholder.com/140x40?text=" + urlEncode(appName)
                : logoUrl;

        return """
            <!doctype html>
            <html lang="en" xmlns="http://www.w3.org/1999/xhtml">
            <head>
              <meta charset="utf-8">
              <meta http-equiv="x-ua-compatible" content="ie=edge">
              <meta name="viewport" content="width=device-width, initial-scale=1">
              <meta name="color-scheme" content="light dark">
              <meta name="supported-color-schemes" content="light dark">
              <title>Reset your %1$s password</title>
              <style>
                html, body { margin:0 !important; padding:0 !important; height:100%% !important; }
                body { width:100%% !important; -webkit-text-size-adjust:100%%; -ms-text-size-adjust:100%%; }
                table, td { mso-table-lspace:0pt; mso-table-rspace:0pt; }
                img { -ms-interpolation-mode:bicubic; border:0; outline:none; text-decoration:none; }
                a { text-decoration:none; }
                .wrapper { width:100%%; background:#f6f8fb; }
                .container { width:100%%; max-width:640px; margin:0 auto; }
                .card { background:#ffffff; border-radius:12px; overflow:hidden; }
                .px { padding-left:24px; padding-right:24px; }
                .py { padding-top:24px; padding-bottom:24px; }
                .center { text-align:center; }
                .muted { color:#6b7280; font-size:14px; line-height:1.6; }
                .heading { font-size:22px; line-height:1.3; color:#111827; margin:0 0 8px 0; }
                .body { font-size:16px; line-height:1.7; color:#1f2937; margin:0; }
                .btn { display:inline-block; padding:14px 22px; border-radius:10px;
                       background:#111827; color:#ffffff !important; font-weight:600; }
                .spacer-16 { height:16px; line-height:16px; font-size:16px; }
                .spacer-24 { height:24px; line-height:24px; font-size:24px; }
                @media (prefers-color-scheme: dark) {
                  .wrapper { background:#0b0f14 !important; }
                  .card { background:#0f1620 !important; }
                  .heading { color:#e5e7eb !important; }
                  .body { color:#d1d5db !important; }
                  .muted { color:#9ca3af !important; }
                  .btn { background:#2563eb !important; }
                }
              </style>
            </head>
            <body style="background:#f6f8fb;">
              <table role="presentation" cellpadding="0" cellspacing="0" class="wrapper" width="100%%">
                <tr>
                  <td align="center">
                    <table role="presentation" cellpadding="0" cellspacing="0" class="container">
                      <tr><td class="spacer-24">&nbsp;</td></tr>
                      <tr>
                        <td class="center">
                          <img src="%6$s" width="140" height="40" alt="%1$s" style="display:block;">
                        </td>
                      </tr>
                      <tr><td class="spacer-24">&nbsp;</td></tr>

                      <tr>
                        <td class="card px py">
                          <table role="presentation" width="100%%" cellpadding="0" cellspacing="0">
                            <tr>
                              <td>
                                <h1 class="heading">Reset your password</h1>
                                <p class="body">We received a request to reset your password. Click the button below:</p>
                              </td>
                            </tr>
                            <tr><td class="spacer-16">&nbsp;</td></tr>
                            <tr>
                              <td class="center">
                                <a class="btn" href="%2$s" target="_blank" rel="noopener">Reset password</a>
                              </td>
                            </tr>
                            <tr><td class="spacer-16">&nbsp;</td></tr>
                            <tr>
                              <td>
                                <p class="muted">
                                  This link will expire in 30 minutes. If you didn’t request this, you can safely ignore this email.
                                </p>
                                <p class="muted">
                                  Having trouble? Paste this link into your browser:<br>
                                  <a href="%2$s" style="word-break:break-all; color:#2563eb;">%2$s</a>
                                </p>
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>

                      <tr><td class="spacer-24">&nbsp;</td></tr>
                      <tr>
                        <td class="px">
                          <table role="presentation" width="100%%" cellpadding="0" cellspacing="0">
                            <tr>
                              <td class="muted" style="font-size:12px;">
                                %3$s • %4$s • %5$s
                              </td>
                            </tr>
                            <tr><td class="spacer-24">&nbsp;</td></tr>
                          </table>
                        </td>
                      </tr>

                    </table>
                  </td>
                </tr>
              </table>
            </body>
            </html>
            """.formatted(
                safe(appName),
                safe(resetUrl),
                safe(appName),
                safe(companyName),
                safe(companyAddress),
                safeLogo
        );
    }

    /* ------------ helpers ------------ */
    private String safe(String s) { return s == null ? "" : s; }

    private String urlEncode(String s) {
        try { return java.net.URLEncoder.encode(s == null ? "" : s, java.nio.charset.StandardCharsets.UTF_8); }
        catch (Exception e) { return ""; }
    }
}
